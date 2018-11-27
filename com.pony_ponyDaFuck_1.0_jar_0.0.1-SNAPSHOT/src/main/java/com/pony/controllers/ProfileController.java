package com.pony.controllers;

import com.pony.entities.dto.ProfileSocialNetworkData;
import com.pony.entities.models.User;
import com.pony.business.services.UserService;
import com.pony.business.social.SocialNetworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('USER')")
public class ProfileController extends BaseController {

    private UserService _userService;
    private SocialNetworkService _socialNetworkService;

    @Autowired
    ProfileController(UserService userService, SocialNetworkService socialNetworkService) {
        _userService = userService;
        _socialNetworkService = socialNetworkService;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {

        User user = _userService.findByMail(this.getConnectedUserMail());

        if (user != null) {
            ProfileSocialNetworkData data =  _socialNetworkService.getSocialData(user);

            _socialNetworkService.getFacebookTest(user);

            return new ModelAndView("profile/profile")
                .addObject("user", user)
                .addObject("data", data);
        }

        return new ModelAndView("error");
    }
}