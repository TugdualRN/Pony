package com.pony.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Controller
@PreAuthorize("hasRole('USER')")
public class ApiController {

    private static Logger _logger = Logger.getLogger(AccountController.class);

    @Value("${twitter.callback}")
    private String _twitterCallback;

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
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl(twitterUrl);
	    return redirectView;
    }

    @RequestMapping("/callback/twitter")
    public ModelAndView twitterCallBack(
        @RequestParam(value="oauth_verifier", required=false) String oauthVerifier,
        @RequestParam(value="denied", required=false) String denied,
        HttpServletRequest request) {
        
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            
            _logger.info("Registered USER ID: " + token.getUserId());
            _logger.info("Registered USER NAME: " + token.getScreenName());
            _logger.info("Registered USER TOKEN: " + token.getToken());
            _logger.info("Registered USER SECRET: " + token.getTokenSecret());

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