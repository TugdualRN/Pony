package com.pony.controllers;

import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.pony.models.Token;
import com.pony.models.User;
import com.pony.services.UserService;
import com.pony.utils.Mailer;
import com.pony.viewmodels.RegisterViewModel;

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
import org.apache.log4j.Logger;

@Controller
public class AccountController {

    private static Logger _logger = Logger.getLogger(AccountController.class);

    private UserService _userService;
    private Mailer _mailer;

    @Autowired
    public AccountController(UserService userService, Mailer mailer) {
        _userService = userService;
        _mailer = mailer;
    }

    @Autowired
    public AccountController(UserService userService)
    {
        _userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(Model model) {

        model.addAttribute("registerViewModel", new RegisterViewModel());
    
        return new ModelAndView("authentication/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView register(@Valid @RequestBody @ModelAttribute RegisterViewModel viewModel, BindingResult bindingResult) {
        
        // Validation
        boolean passworsMatch = viewModel.getPassword().equals(viewModel.getConfirmPassword());
        if (bindingResult.hasErrors() || !passworsMatch)
        {   
            return new ModelAndView("authentication/register");
        }

        // Creation
        User user = _userService.createUser(new User(viewModel.getUserName(), viewModel.getMail()), viewModel.getPassword());

        // Verification
        if (user != null) {

            if (_mailer != null) {
                _mailer.sendMail(user.getMail(), "TEST", "TEST");
            }

            return new ModelAndView("authentication/register-success");
        }

        return new ModelAndView("authentication/register");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {

        return new ModelAndView("authentication/login");
    }

    @RequestMapping(value = "/login/fail", method = RequestMethod.GET)
    public ModelAndView loginFailure() {

        return new ModelAndView("authentication/login-failure");
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirmAccount(@RequestParam long userId, @RequestParam String tokenValue) {
        
        User user = _userService.findById(userId);

        if (user != null) {
            try {
                // retrieve token matching the given one
                Token token = user.getTokens().stream().filter(x -> x.getUuid().toString() == tokenValue).findFirst().get();
                
                Hours hours = Hours.between(token.getGenerationDate(), new Date());
                // calculate interval for token validity
                if (interval.toPeriod().getHours() < 48) {

                }
            }
            catch (NoSuchElementException e) {
                _logger.error("No token found", e);

            }
        }

        return new ModelAndView("authentication/confirmSuccess");
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