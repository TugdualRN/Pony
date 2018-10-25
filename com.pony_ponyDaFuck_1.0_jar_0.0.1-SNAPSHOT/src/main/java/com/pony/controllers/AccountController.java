package com.pony.controllers;

import javax.validation.Valid;

import com.pony.enumerations.TokenType;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.services.TokenService;
import com.pony.services.UserService;
import com.pony.utils.RegisterResult;
import com.pony.viewmodels.ForgotPasswordViewModel;
import com.pony.viewmodels.RegisterViewModel;
import com.pony.viewmodels.ResetPasswordViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AccountController {

    private final Logger _logger = LoggerFactory.getLogger(AccountController.class);

    private UserService _userService;
    private TokenService _tokenService;

    @Autowired
    public AccountController(UserService userService, TokenService tokenService) {
        _tokenService = tokenService;
        _userService = userService;
    }

    // <editor-fold desc="Register">
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(Model model) {
    
        return new ModelAndView("authentication/register")
            .addObject("registerViewModel", new RegisterViewModel())
            .addObject("errors", new RegisterResult());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView register(@Valid @RequestBody @ModelAttribute RegisterViewModel viewModel, BindingResult bindingResult) {
        
        // Validation
        boolean passworsMatch = viewModel.getPassword().equals(viewModel.getConfirmPassword());
        if (bindingResult.hasErrors() || !passworsMatch)
        {   
            return new ModelAndView("authentication/register")
                .addObject("registerViewModel", viewModel)
                .addObject("errors", new RegisterResult());
        }

        // Creation
        RegisterResult registerResult = _userService.createUser(
            new User(viewModel.getUserName(), viewModel.getMail()),
            viewModel.getPassword()
        );

        // Mailing
        if (registerResult.isValid()) {
            try {
                _userService.generateToken(registerResult.getUser(), TokenType.ACTIVATE_ACCOUNT);
            } catch (MailException e) {
                _logger.error("Mailing connection timeout");

                throw e;
            }

            return new ModelAndView("authentication/register-success");
        }

        // return RegisterResult to display in view
        return new ModelAndView("authentication/register")
            .addObject("registerViewModel", viewModel)
            .addObject("errors", registerResult);
    }
    // </editor-fold>

    // <editor-fold desc="Login">
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {

        return new ModelAndView("authentication/login");
    }

    @RequestMapping(value = "/login/fail", method = RequestMethod.GET)
    public ModelAndView loginFailure() {

        return new ModelAndView("authentication/login-failure");
    }

    /*
     *  The Login POST Method is handled by Spring Security
     */
    // </editor-fold>

    // <editor-fold desc="Confirmation">
    @RequestMapping(value = "/confirm-mail", method = RequestMethod.GET)
    public ModelAndView confirmAccount(@RequestParam long userId, @RequestParam String tokenValue) {
        
        User user = _userService.findById(userId);

        if (user != null) {
            // retrieve token matching the given one
            Token token = _tokenService.findToken(tokenValue, user.getTokens(), TokenType.ACTIVATE_ACCOUNT);
            
            // Check if token didn't expire
            if (_tokenService.isValidToken(token)) {
                _tokenService.consumeToken(token);
                user.setIsActive(true);
                _userService.update(user);
                
                return new ModelAndView("authentication/confirm-success");
            }
        }

        return new ModelAndView("authentication/confirm-failure");
    }
    // </editor-fold>

    // <editor-fold desc="PasswordReset">
    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public ModelAndView resetPassword() {

        return new ModelAndView("authentication/reset-password")
            .addObject("forgotPasswordViewModel", new ForgotPasswordViewModel());
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView resetPassword(@Valid @RequestBody @ModelAttribute ForgotPasswordViewModel viewModel, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())   
            return new ModelAndView("home");

        User user = _userService.findByNormalizedMail(viewModel.getMail().toUpperCase());
        
        if (user != null) {
            _userService.generateToken(user, TokenType.RESET_PASSWORD);

            return new ModelAndView("authentication/reset-password-success");
        }

        return new ModelAndView("authentication/reset-password-failure");
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public ModelAndView changePassword(@RequestParam long userId, @RequestParam String tokenValue) {
        return new ModelAndView("authentication/change-password")
            .addObject("userId", userId)
            .addObject("token", tokenValue);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView changePassword(@Valid @RequestBody @ModelAttribute ResetPasswordViewModel viewModel, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return new ModelAndView("home");

        User user = _userService.findById(viewModel.getUserId());

        if (user != null) {
            Token token = _tokenService.findToken(viewModel.getToken(), user.getTokens(), TokenType.RESET_PASSWORD);

            if (_tokenService.isValidToken(token)) {
                _userService.updatePassword(user, viewModel.getPassword());

                return new ModelAndView("authentication/change-password-success");
            }
        }

        return new ModelAndView("authentication/change-password-failure");
    }
    // </editor-fold>
}