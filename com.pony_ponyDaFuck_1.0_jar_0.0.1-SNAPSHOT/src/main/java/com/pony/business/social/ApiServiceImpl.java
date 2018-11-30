package com.pony.business.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;

import com.pony.enumerations.SocialNetworkType;
import com.pony.entities.models.SocialNetwork;
import com.pony.entities.models.User;
import com.pony.business.social.ApiService;

/**
 * Class used to manipulate "low level" API interactions
 */
@Service
public class ApiServiceImpl implements ApiService {

    private static final Logger _logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    private TwitterFactory  _twitterFactory;
    private FacebookFactory _facebookFactory;

    @Value("${twitter.callback}")
    private String _twitterCallback;

    @Value("${facebook.callback}")
    private String _facebookCallback;

    @Autowired
    public ApiServiceImpl(TwitterFactory twitterFactory, FacebookFactory facebookFactory) {
        _twitterFactory = twitterFactory;
        _facebookFactory = facebookFactory;
    }

    public boolean isValidCallback(String oauthVerifier, String denied) {
        return oauthVerifier != null && !oauthVerifier.isEmpty() && denied == null;
    }

    // Tweeter use a Request Token
    public RequestToken getTwitterRequestToken() throws TwitterException {
        return _twitterFactory.getInstance().getOAuthRequestToken(_twitterCallback);	
    }

    // Facebook only use the Redirect Url
    public String getFacebookRedirectUrl() {
        return _facebookFactory.getInstance().getOAuthAuthorizationURL(_facebookCallback);
    }

    public Twitter getTwitter() {
        return _twitterFactory.getInstance();
    }

    public Facebook getFacebook() {
        return _facebookFactory.getInstance();
    }

    public Facebook getFacebook(facebook4j.auth.AccessToken accessToken) {
        return _facebookFactory.getInstance(accessToken);
    }

    public Twitter getTwitter(AccessToken accessToken) {
        return _twitterFactory.getInstance(accessToken);
    }

    public Twitter getTwitter(String token, String tokenSecret) {
        return _twitterFactory.getInstance(new AccessToken(token, tokenSecret));
    }

    public SocialNetwork createSocialNetwork(SocialNetworkType type, String accesToken, String tokenSecret) {
        return new SocialNetwork(type, accesToken, tokenSecret);
    }

    public boolean createTwitterSocialNetwork(User user, RequestToken requestToken, String oauthVerifier) {
        try {
            AccessToken accessToken = this.getTwitter().getOAuthAccessToken(requestToken, oauthVerifier);

            SocialNetwork socialNetwork = new SocialNetwork(SocialNetworkType.TWITTER, 
                accessToken.getToken(),
                accessToken.getTokenSecret());

            if (!this.userHasSocialNetwork(user, SocialNetworkType.TWITTER)) {
                user.getSocialNetworks().put(socialNetwork.getSocialNetworkType(), socialNetwork);
                socialNetwork.setUser(user);

                return true;
            }

            _logger.error("User {} already has a linked {} social network", user.getMail(), SocialNetworkType.TWITTER);

            return false;
        }
        catch (TwitterException e) { 
            _logger.error("Error while trying to retrieve the twitter access token", e);
            
            return false;
        }
    }

    public boolean userHasSocialNetwork(User user, SocialNetworkType socialNetworkType) {
        if (user.getSocialNetworks().containsKey(socialNetworkType)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean createFacebookSocialNetwork(User user, String oauthVerifier, Facebook facebook) {
        try {
            //Facebook facebook = this.getFacebook();

            // Access Token given by facebook are short lived by default (hourly timed)
            // facebook4j.auth.AccessToken token = facebook.getOAuthAccessToken(oauthVerifier, _facebookCallback);
            facebook4j.auth.AccessToken token = facebook.getOAuthAccessToken(oauthVerifier);


            // We can translate them to long lived tokens (60 days)
            //token = facebook.extendTokenExpiration(token.getToken());

            SocialNetwork socialNetwork = new SocialNetwork(SocialNetworkType.FACEBOOK, 
                token.getToken(),
                "");

            if (!this.userHasSocialNetwork(user, SocialNetworkType.FACEBOOK)) {
                user.getSocialNetworks().put(socialNetwork.getSocialNetworkType(), socialNetwork);
                socialNetwork.setUser(user);
                return true;
            }

            _logger.error("User {} already has a linked {} social network", user.getMail(), SocialNetworkType.TWITTER);

            return false;
        }
        catch (FacebookException e) {
            _logger.error("Error while trying to retrieve the facebook access token", e);

            return false;
        }
    }
}