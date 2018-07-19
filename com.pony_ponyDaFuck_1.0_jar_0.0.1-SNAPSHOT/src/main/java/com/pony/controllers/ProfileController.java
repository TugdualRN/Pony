package com.pony.controllers;

import javax.validation.Valid;

import com.pony.models.User;
import com.pony.services.UserService;
import com.pony.viewmodels.RegisterViewModel;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('USER')")
public class ProfileController {

    private UserService _userService;

    ProfileController(UserService userService) {
        _userService = userService;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String mail = authentication.getName();
            User user = _userService.findByMail(mail);

            return new ModelAndView("profile/profile")
                .addObject("user", user);
        }

        return new ModelAndView("/");
    }
}