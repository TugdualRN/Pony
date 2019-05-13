package com.pony.business.social;

import com.pony.entities.models.User;
import com.pony.enumerations.SocialNetworkType;
import facebook4j.Facebook;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Interface used to manipulate "low level" API interactions
 */
public interface ApiService {

    public boolean isValidCallback(String oauthVerifier, String denied);

    public RequestToken getTwitterRequestToken() throws TwitterException;

    public Twitter getTwitter();

    public Twitter getTwitter(AccessToken accessToken);

    public Twitter getTwitter(String token, String tokenSecret);

    public String getFacebookRedirectUrl();

    public Facebook getFacebook();

    // public Facebook getFacebook(AccessToken accessToken);

    // public Facebook getFacebook(String token, String tokenSecret);

    public boolean createTwitterSocialNetwork(User user, RequestToken requestToken, String oauthVerifier);

    public boolean createFacebookSocialNetwork(User user, String oauthVerifier);

    public boolean userHasSocialNetwork(User user, SocialNetworkType socialNetworkType);
}