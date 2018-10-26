package com.pony.controllers;

import javax.servlet.http.HttpServletRequest;

import com.pony.enumerations.SocialNetworkType;
import com.pony.entities.models.SocialNetwork;
import com.pony.entities.models.User;
import com.pony.business.services.ApiService;
import com.pony.business.services.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
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
    public RedirectView getTwitterToken(HttpServletRequest request, Model model) throws TwitterException {

        try {
            RequestToken requestToken = _apiService.getTwitterRequestToken();		
        
            //put the token in the session because we'll need it later
            request.getSession().setAttribute("requestToken", requestToken);
            
            return new RedirectView(requestToken.getAuthorizationURL());
        }
        catch (TwitterException e) {
            return new RedirectView("http://localhost:8000/error");
        }
    }

    @RequestMapping("/callback/twitter")
    public ModelAndView twitterCallBack(
        @RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
        @RequestParam(value="denied", required=false) String denied,
        HttpServletRequest request) {
        
        if (_apiService.isValidCallback(oauthVerifier, denied)) {

            RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");

            if (requestToken != null) {
                AccessToken accessToken;
                try {
                    accessToken = _apiService.getTwitter().getOAuthAccessToken(requestToken, oauthVerifier);
                }
                catch (TwitterException e) { 
                    _logger.error("Error while trying to retrieve the twitter access token", e);
                    return new ModelAndView("error").addObject("error", e);
                }

                User user = _userService.findByMail(this.getConnectedUserMail());

                if (user != null) {
                    SocialNetwork socialNetwork = new SocialNetwork(SocialNetworkType.TWITTER, 
                        accessToken.getToken(),
                        accessToken.getTokenSecret());

                    if (!_apiService.userHasSocialNetwork(user, SocialNetworkType.TWITTER)) {
                        user.getSocialNetworks().put(socialNetwork.getSocialNetworkType(), socialNetwork);
                        socialNetwork.setUser(user);

                        _userService.update(user);

                        return new ModelAndView("redirect:/");
                    }

                    _logger.error("User " + user.getUserName() + "already had a social network for this type, which should not be possible");
                }

                _logger.error("User is null, which should not be possible");
            }
        }

        _logger.info("User refused Twitter");
        
        return new ModelAndView("error");
    }
}