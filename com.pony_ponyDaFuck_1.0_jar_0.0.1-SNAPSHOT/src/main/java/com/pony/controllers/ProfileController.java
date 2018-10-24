package com.pony.controllers;


import java.util.ArrayList;
import java.util.List;

import com.pony.enumerations.SocialNetworkType;
import com.pony.models.SocialNetwork;
import com.pony.models.User;
import com.pony.services.ApiService;
import com.pony.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Controller
@PreAuthorize("hasRole('USER')")
public class ProfileController extends BaseController {

    private UserService _userService;
    private ApiService _apiService;

    @Autowired
    ProfileController(UserService userService, ApiService apiService) {
        _userService = userService;
        _apiService = apiService;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {

        User user = _userService.findByMail(this.getConnectedUserMail());

        SocialNetwork twittus = user.getSocialNetworks().get(SocialNetworkType.TWITTER);

        Twitter twitter = _apiService.getTwitter(twittus.getAccesstoken(), twittus.getTokensecret());

        List<Status> tweets = new ArrayList<Status>();
        twitter4j.User infos = null;
        try {
            tweets = twitter.getUserTimeline();
            infos = twitter.showUser(twitter.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
		}

        return new ModelAndView("profile/profile")
            .addObject("user", user)
            .addObject("infos", infos)
            .addObject("tweets", tweets);
    }
}