package com.pony.controllers;

import javax.servlet.http.HttpServletRequest;

import com.pony.services.ApiService;
import com.pony.services.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@PreAuthorize("hasRole('USER')")
public class ApiController {

    private static Logger _logger = LoggerFactory.getLogger(AccountController.class);

    @Value("${twitter.callback}")
    private String _twitterCallback;

    private UserService _userService;
    private ApiService _apiService;

    public ApiController(UserService userService, ApiService apiService)
    {
        _userService = userService;
        _apiService = apiService;
    }

    @RequestMapping("/connect/twitter")
    public RedirectView getTwitterToken(HttpServletRequest request, Model model) {
        String twitterUrl = "";
    	
		try {
			//get the Twitter object
			Twitter twitter = getTwitter();
			
			//get the callback url so they get back here
			//go get the request token from Twitter
			RequestToken requestToken = twitter.getOAuthRequestToken(_twitterCallback);
			
			//put the token in the session because we'll need it later
			request.getSession().setAttribute("requestToken", requestToken);
			//let's put Twitter in the session as well
			request.getSession().setAttribute("twitter", twitter);
			
			//now get the authorization URL from the token
			twitterUrl = requestToken.getAuthorizationURL();
			
			System.out.println("Authorization url is " + twitterUrl);
		} catch (Exception e) {
			System.out.println("Problem logging in with Twitter! " + e.getClass());
		}
    	
		//redirect to the Twitter URL
	    return new RedirectView(twitterUrl);
    }

    @RequestMapping("/callback/twitter")
    public ModelAndView twitterCallBack(
        @RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
        @RequestParam(value="denied", required=false) String denied,
        HttpServletRequest request
    ) {
        
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

            System.out.println("REQUEST_TOKEN TOKEN : " + requestToken.getToken());
            System.out.println("ACCESS_TOKEN : " + token.getToken());
            System.out.println("ACCESS_TOKEN SECRET : " + token.getTokenSecret());
            
            System.out.println("Registered USER ID: " + token.getUserId());
            System.out.println("Registered USER NAME: " + token.getScreenName());
            System.out.println("Registered USER TOKEN: " + token.getToken());
            System.out.println("Registered USER SECRET: " + token.getTokenSecret());

            ResponseList<Status> lastStatus = twitter.getUserTimeline();

            System.out.println("----");
            System.out.println();

            for (Status status : lastStatus)
            {
                System.out.println(status.getText());
                System.out.println();
            }

            return new ModelAndView("/profile/twitter.html").addObject("twitter", twitter);
        }
        catch (TwitterException e) {
            System.out.println(e.toString());
        }

        return null;
    }

    // Move this shit in a service
    private Twitter getTwitter()
    {
        // Set the consumer key and secret for our app
        String consumerKey = "MyKfxtg9Qi5XkvHlvKq1phf5m";
        String consumerSecret = "aM4hsNAWLgn7jAMDKwYMJY2oCfKNVpXnkTYia1bel87bV34Jbp";
        
        // Build the configuration
        Configuration configuration = new ConfigurationBuilder()
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .build();

        // Instantiate the Twitter object with the configuration
        return new TwitterFactory(configuration).getInstance();
    }
}