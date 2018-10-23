package com.pony.services;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public interface ApiService {

    public boolean isValidCallback(String oauthVerifier, String denied);

    public RequestToken getTwitterRequestToken() throws TwitterException, IllegalStateException ;

    public Twitter getTwitter();

    public Twitter getTwitter(AccessToken accessToken);

    public Twitter getTwitter(String token, String tokenSecret);
}