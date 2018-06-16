package com.pony.controllers;

import javax.validation.Valid;

import com.pony.models.User;
import com.pony.services.UserService;
import com.pony.viewmodels.RegisterViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    @Autowired
    private UserService _userService;

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
        User user = new User(viewModel.getUserName(), viewModel.getMail());
        User savedUser = _userService.createUser(user, viewModel.getPassword());

        // Verification
        if (savedUser != null) {
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

    /**
     * The POST Login action is Handled By SpringSecurity
     * Login logic is handled in pony.security.UserDetailsServiceImpl.loadUserByUsername(String login)
     * The Above Method will be called by Spring Security Authentication Middleware
     */

    // @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    // public String login(@Valid @RequestBody @ModelAttribute LoginViewModel viewModel, BindingResult bindingResult) {
    // }
}