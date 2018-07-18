package com.pony.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import javax.validation.Valid;

import com.pony.enumerations.TokenType;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.services.TokenService;
import com.pony.services.UserService;
import com.pony.utils.Mailer;
import com.pony.utils.RegisterResult;
import com.pony.viewmodels.ForgotPasswordViewModel;
import com.pony.viewmodels.RegisterViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

@Controller
public class AccountController {

    private static Logger _logger = Logger.getLogger(AccountController.class);

    private UserService _userService;
    private TokenService _tokenService;
    private Mailer _mailer;

    @Autowired
    public AccountController(UserService userService, TokenService tokenService, Mailer mailer) {
        _tokenService = tokenService;
        _userService = userService;
        _mailer = mailer;
    }

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
                // Generate Token and Update User
                Token token = _tokenService.generateToken(TokenType.ACTIVATE_ACCOUNT, registerResult.getUser());
                registerResult.getUser().getTokens().add(token);
                _userService.update(registerResult.getUser());

                // Send Mail
                _mailer.SendRegisterMail(registerResult.getUser(), token);
            } catch (MailSendException e) {
                _logger.fatal("Mailing connection timeout");
            }

            return new ModelAndView("authentication/register-success");
        }

        // return RegisterResult to display in view
        return new ModelAndView("authentication/register")
            .addObject("registerViewModel", viewModel)
            .addObject("errors", registerResult);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {

        return new ModelAndView("authentication/login");
    }

    @RequestMapping(value = "/login/fail", method = RequestMethod.GET)
    public ModelAndView loginFailure() {

        return new ModelAndView("authentication/login-failure");
    }

    @RequestMapping(value = "/confirm-email", method = RequestMethod.GET)
    public ModelAndView confirmAccount(@RequestParam long userId, @RequestParam String tokenValue) {
        
        User user = _userService.findById(userId);

        if (user != null) {
            try {
                // retrieve token matching the given one
                Token token = user.getTokens().stream().filter(x -> x.getValue().toString() == tokenValue).findFirst().get();
                
                LocalDateTime now = LocalDateTime.now();

                if (Duration.between(token.getCreationdate(), now).toHours() < 48) {
                    user.setIsActive(true);
                    _userService.update(user);
                }
            }
            catch (NoSuchElementException e) {
                _logger.error("No token found", e);

            }
        }

        return new ModelAndView("authentication/confirmSuccess");
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public ModelAndView resetPassword() {

        return new ModelAndView("authentication/reset-password")
            .addObject("forgotPasswordViewModel", new ForgotPasswordViewModel());
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView resetPassword(@Valid @RequestBody @ModelAttribute ForgotPasswordViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        {   
            return new ModelAndView("home");
        }

        User user = _userService.findByNormalizedMail(viewModel.getMail().toUpperCase());
        
        if (user != null) {
            Token token = _tokenService.generateToken(TokenType.RESET_PASSWORD, user);
            user.getTokens().add(token);
            _userService.update(user);

            _mailer.SendResetPassword(user, token);

            return new ModelAndView("authentication/reset-password-success");
        }

        return new ModelAndView("authentication/reset-password-failure");
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public ModelAndView changePassword(@RequestParam long userId, @RequestParam String tokenValue)
    {
        User user = _userService.findById(userId);

        if (user != null) {
            //Token token = user.getTokens().str
            
        }

        return null;
    }

    /**
     * The POST Login action is Handled By SpringSecurity
     * Login logic is handled in pony.security.UserDetailsServiceImpl.loadUserByUsername(String login)
     * The Above Method will be called by Spring Security Authentication Middleware
     */

    // @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    // public ModelAndView login(@Valid @RequestBody @ModelAttribute LoginViewModel viewModel, BindingResult bindingResult) {
    // }
}