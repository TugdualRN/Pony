package com.pony.services.impl;

import com.pony.enumerations.SocialNetworkType;
import com.pony.models.SocialNetwork;
import com.pony.models.User;
import com.pony.services.ApiService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Service
public class ApiServiceImpl implements ApiService {

    private TwitterFactory _twitterFactory;

    @Value("${twitter.callback}")
    private String _twitterCallback;

    public ApiServiceImpl(TwitterFactory twitterFactory) {
        _twitterFactory = twitterFactory;
    }

    public boolean isValidCallback(String oauthVerifier, String denied) {
        return oauthVerifier != null && !oauthVerifier.isEmpty() && denied == null;
    }

    public RequestToken getTwitterRequestToken() throws TwitterException, IllegalStateException {
        return _twitterFactory.getInstance().getOAuthRequestToken(_twitterCallback);	
    }

    // public AccessToken getTwitterAccessToken() {
    //     return _twitterFactory.getInstance()
    // }

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

    public boolean userHasSocialNetwork(User user, SocialNetworkType socialNetworkType) {
        if (user.getSocialNetworks().containsKey(socialNetworkType)) {
            return true;
        }

        return false;
    }
}