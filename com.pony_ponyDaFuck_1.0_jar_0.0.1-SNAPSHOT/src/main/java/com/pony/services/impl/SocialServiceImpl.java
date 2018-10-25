package com.pony.services.impl;

import com.pony.services.SocialService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class SocialServiceImpl implements SocialService {

    private final Logger _logger = LoggerFactory.getLogger(SocialServiceImpl.class);

    @Value("${twitter.consumerKey}")
    private String _twitterConsumerKey;

    @Value("${twitter.consumerSecret")
    private String _twitterConsumerSecret;

    @Value("${facebook.appId}")
    private String _facebookAppId;

    @Value("${facebook.appSecret}")
    private String _facebookAppSecret;

    public Twitter getTwitter()
    {
        // Build the configuration
        twitter4j.conf.Configuration configuration = new twitter4j.conf.ConfigurationBuilder()
            .setOAuthConsumerKey(_twitterConsumerKey)
            .setOAuthConsumerSecret(_twitterConsumerSecret)
            .build();

        TwitterFactory factory = new TwitterFactory(configuration);

        _logger.info("Created Twitter Factory with Consumer Key {} and Consumer Secret {}", _twitterConsumerKey, _twitterConsumerSecret);

        return factory.getInstance();
    }

    public Facebook getFacebook()
    {
        facebook4j.conf.Configuration configuration = new facebook4j.conf.ConfigurationBuilder()
            .setOAuthAppId(_facebookAppId)
            .setOAuthAppSecret(_facebookAppSecret)
            .build();

        FacebookFactory factory = new FacebookFactory(configuration);

        _logger.info("Created Facebook Factory with Application Id {} and Consumer Secret {}", _facebookAppId, _facebookAppSecret);

        return factory.getInstance();
    }
}