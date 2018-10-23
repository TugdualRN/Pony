package com.pony.services.impl;

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
}