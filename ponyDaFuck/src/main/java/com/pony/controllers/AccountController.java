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

@Controller
public class AccountController {

    @Autowired
    private UserService _userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("registerViewModel", new RegisterViewModel());
    
        return "authentication/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public String register(@Valid @RequestBody @ModelAttribute RegisterViewModel viewModel, BindingResult bindingResult) {
        
        boolean passworsMatch = viewModel.getPassword().equals(viewModel.getConfirmPassword());

        if (bindingResult.hasErrors() || !passworsMatch)
        {   
            return "authentication/register";
        }

        User user = new User();
        user.setUserName(viewModel.getUserName());
        user.setMail(viewModel.getMail());
        user.setPasswordHash(viewModel.getPassword());

        try {
            _userService.insert(user);
        } catch (Exception e) {
            System.out.println("\n FOUND AN APPLICATION EXCEPTION \n");
            System.out.println(e);
        }

        return "authentication/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "authentication/login";
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