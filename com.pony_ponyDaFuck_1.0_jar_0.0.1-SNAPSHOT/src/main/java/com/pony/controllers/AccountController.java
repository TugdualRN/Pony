package com.pony.controllers;

import javax.validation.Valid;

import com.pony.enumerations.TokenType;
import com.pony.entities.models.Token;
import com.pony.entities.models.User;
import com.pony.business.services.TokenService;
import com.pony.business.services.UserService;
import com.pony.business.utils.RegisterResult;
import com.pony.business.utils.mailing.MailService;
import com.pony.views.viewmodels.ForgotPasswordViewModel;
import com.pony.views.viewmodels.RegisterViewModel;
import com.pony.views.viewmodels.ResetPasswordViewModel;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountController extends BaseController {

    private final Logger _logger = LoggerFactory.getLogger(AccountController.class);

    private UserService _userService;
    private TokenService _tokenService;
    private MailService _mailService;

    @Autowired
    public AccountController(UserService userService, TokenService tokenService, MailService mailService) {
        _tokenService = tokenService;
        _userService = userService;
        _mailService = mailService;
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
        User user = new User(viewModel.getUserName(), viewModel.getMail());
        RegisterResult registerResult = _userService.createUser(user, viewModel.getPassword());

        // Create Register Token and send mail
        if (registerResult.isValid()) {
            _userService.linkTokenToUser(registerResult.getUser(), TokenType.ACTIVATE_ACCOUNT);

            if (_mailService.SendRegisterMail(user, user.getTokens().get(0))) {
                return this.returnToSuccessPage("Your account was successfully created");
            }
        }

        // Mail or userName is taken, return to the form with the informations
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
        return this.returnToErrorPage("Login failure");
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
                _userService.activateUser(user);
                
                return this.returnToSuccessPage("Your account has been successfully activated");
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
            Token token = _tokenService.generateToken(TokenType.RESET_PASSWORD, user);
            user.getTokens().add(token);
            User savedUser = _userService.update(user);

            _mailService.SendResetPassword(savedUser, token);

            return this.returnToSuccessPage("Check your e-mails to reset your password");
        }

        return this.returnToErrorPage("An error occured while reseting your password");
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public ModelAndView changePassword(@RequestParam long userId, @RequestParam String tokenValue) {
        return new ModelAndView("authentication/change-password")
            .addObject("viewModel", new ResetPasswordViewModel(userId, tokenValue));
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView changePassword(@Valid @RequestBody @ModelAttribute ResetPasswordViewModel viewModel, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return new ModelAndView("home");

        User user = _userService.findById(viewModel.getUserId());

        if (user != null) {
            Token token = _tokenService.findToken(viewModel.getToken(), user.getTokens(), TokenType.RESET_PASSWORD);

            if (_tokenService.isValidToken(token)) {
                _tokenService.consumeToken(token);
                _userService.updatePassword(user, viewModel.getPassword());

                return this.returnToSuccessPage("Your password was successfully changed");
            }
        }

        return this.returnToErrorPage("An error occured while resetting your password");
    }
    // </editor-fold>
}