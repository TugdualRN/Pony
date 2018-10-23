package com.pony.services.impl;

import com.pony.services.SocialService;

import org.springframework.beans.factory.annotation.Value;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SocialServiceImpl implements SocialService {

    @Value("${twitter.consumerKey}")
    private String _consumerKey;

    @Value("${twitter.consumerSecret")
    private String _consumerSecret;

    public Twitter getTwitter()
    {
        // Build the configuration
        Configuration configuration = new ConfigurationBuilder()
            .setOAuthConsumerKey(_consumerKey)
            .setOAuthConsumerSecret(_consumerSecret)
            .build();

        // Instantiate the Twitter object with the configuration
        return new TwitterFactory(configuration).getInstance();
    }
}