package com.pony.controllers;

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

        if (user != null) {
            SocialNetwork twittus = user.getSocialNetworks().get(SocialNetworkType.TWITTER);

            List<Status> tweets = null;
            twitter4j.User infos = null;

            if (twittus != null) {
                Twitter twitter = _apiService.getTwitter(twittus.getAccesstoken(), twittus.getTokensecret());
    
                try {
                    tweets = twitter.getUserTimeline();
                    infos = twitter.showUser(twitter.getId());
                } catch (TwitterException e) { }
            }

            return new ModelAndView("profile/profile")
                .addObject("user", user)
                .addObject("infos", infos)
                .addObject("tweets", tweets);
        }

        return new ModelAndView("error");
    }
}