package com.pony.controllers;

import javax.servlet.http.HttpServletRequest;

import com.pony.entities.models.User;
import com.pony.business.social.ApiService;
import com.pony.business.services.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@PreAuthorize("hasRole('USER')")
public class ApiController extends BaseController {

    private static Logger _logger = LoggerFactory.getLogger(AccountController.class);

    @Value("${twitter.callback}")
    private String _twitterCallback;

    private UserService _userService;
    private ApiService _apiService;

    public ApiController(UserService userService, ApiService apiService) {
        _userService = userService;
        _apiService = apiService;
    }

    @RequestMapping("/connect/twitter")
    public RedirectView twitterLink(HttpServletRequest request) throws TwitterException {

        try {
            RequestToken requestToken = _apiService.getTwitterRequestToken();		
            request.getSession().setAttribute("requestToken", requestToken);
            
            return new RedirectView(requestToken.getAuthorizationURL());
        }
        catch (TwitterException e) {
            _logger.error("An exception occured while trying to create Twitter redirect url");

            // TO DO => Try to return the user to the general error page with a message (don't know how to do it with a RedirectView)
            return new RedirectView("http://localhost:8000/error");
        }
    }

    @RequestMapping("/callback/twitter")
    public ModelAndView twitterCallback(HttpServletRequest request,
        @RequestParam(value = "oauth_verifier", required = false) String oauthVerifier,
        @RequestParam(value = "denied",         required = false) String denied) {
        
        if (_apiService.isValidCallback(oauthVerifier, denied)) {

            RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            User user = _userService.findByMail(this.getConnectedUserMail());

            if (requestToken != null && user != null) {
                if (_apiService.createTwitterSocialNetwork(user, requestToken, oauthVerifier)) {
                    _userService.update(user);

                    return new ModelAndView("redirect:/profile");
                }

                _logger.error("Error while creating a social network for user {}", user.getMail());
            }

            _logger.error("Cached token or user is null");

            return this.returnToErrorPage("An error occured while linking your social network to your account");
        }

        _logger.info("User refused Twitter");

        return this.returnToErrorPage("Your refused to link your account");
    }

    @RequestMapping("/connect/facebook")
    public RedirectView facebookLink(HttpServletRequest request) {
        return new RedirectView(_apiService.getFacebookRedirectUrl());
    }

    @RequestMapping("/callback/facebook")
    public ModelAndView facebookCallback(HttpServletRequest request, 
        @RequestParam(value = "code", required = false) String oauthVerifier) {

        if (_apiService.isValidCallback(oauthVerifier, null)) {
            
            User user = _userService.findByMail(this.getConnectedUserMail());
            if (oauthVerifier != null && user != null) {
                if (_apiService.createFacebookSocialNetwork(user, oauthVerifier)) {
                    _userService.update(user);

                    return new ModelAndView("redirect:/profile");
                }

                _logger.error("Error while creating a social network for user {}", user.getMail());

                return this.returnToSuccessPage("Callback was successful but it's still work in progress so :shrug:");
            }

            return this.returnToErrorPage("An error occured :(");

        }

        return this.returnToErrorPage("Your refused to link your account");
    }

    @RequestMapping("/callback/facebook/extend_token")
    public ModelAndView facebookExtendTokenCallback(HttpServletRequest request, 
        @RequestParam(value = "code", required = false) String oauthVerifier) {
        
        return null;
    }
}