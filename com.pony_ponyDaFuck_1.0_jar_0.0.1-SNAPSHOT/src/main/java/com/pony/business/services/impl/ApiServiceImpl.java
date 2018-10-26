package com.pony.business.services.impl;

import com.pony.enumerations.SocialNetworkType;
import com.pony.entities.models.SocialNetwork;
import com.pony.entities.models.User;
import com.pony.business.services.ApiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Service
public class ApiServiceImpl implements ApiService {

    private static final Logger _logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    private TwitterFactory _twitterFactory;

    @Value("${twitter.callback}")
    private String _twitterCallback;

    @Autowired
    public ApiServiceImpl(TwitterFactory twitterFactory) {
        _twitterFactory = twitterFactory;
    }

    public boolean isValidCallback(String oauthVerifier, String denied) {
        return oauthVerifier != null && !oauthVerifier.isEmpty() && denied == null;
    }

    public RequestToken getTwitterRequestToken() throws TwitterException, IllegalStateException {
        return _twitterFactory.getInstance().getOAuthRequestToken(_twitterCallback);	
    }

    public Twitter getTwitter() {
        return _twitterFactory.getInstance();
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

    public boolean createSocialNetwork(User user, RequestToken requestToken, String oauthVerifier) {
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
}