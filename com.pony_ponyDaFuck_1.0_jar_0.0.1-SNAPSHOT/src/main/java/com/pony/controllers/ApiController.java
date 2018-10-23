package com.pony.controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpServletRequest;

import com.pony.services.ApiService;
import com.pony.services.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
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
    public ModelAndView getTwitterToken(HttpServletRequest request, Model model) throws TwitterException {

        try {
            RequestToken requestToken = _apiService.getTwitterRequestToken();		
        
            //put the token in the session because we'll need it later
            request.getSession().setAttribute("requestToken", requestToken);
            
            //now get the authorization URL from the token
            String twitterUrl = requestToken.getAuthorizationURL();
            
            return new ModelAndView("forward:" + twitterUrl);
        }
        catch (TwitterException e) {
            return new ModelAndView("error").addObject("error", e);
        }
    }

    @RequestMapping("/callback/twitter")
    public ModelAndView twitterCallBack(
        @RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
        @RequestParam(value="denied", required=false) String denied,
        HttpServletRequest request) {
        
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (_apiService.isValidCallback(oauthVerifier, denied)) {

            try {
                RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            
                Twitter twitter = _apiService.getTwitter();
                AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
    
                printDebug(token, twitter);
            }
            catch (TwitterException e) { }

        }

        return new ModelAndView("/home");
    }

    public static void printDebug(AccessToken token, Twitter twitter)
    {
        //System.out.println("REQUEST_TOKEN TOKEN : " + requestToken.getToken());
        System.out.println("ACCESS_TOKEN : " + token.getToken());
        System.out.println("ACCESS_TOKEN SECRET : " + token.getTokenSecret());
        
        System.out.println("Registered USER ID: " + token.getUserId());
        System.out.println("Registered USER NAME: " + token.getScreenName());
        System.out.println("Registered USER TOKEN: " + token.getToken());
        System.out.println("Registered USER SECRET: " + token.getTokenSecret());

        try {
            ResponseList<Status> lastStatus = twitter.getUserTimeline();

            System.out.println("----");
            System.out.println();
    
            for (Status status : lastStatus)
            {
                System.out.println(status.getText());
                System.out.println();
            }
        }
        catch (Exception e) { }
    }
}