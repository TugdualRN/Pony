package com.pony.spring.injection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class SocialConfig {

    @Value("${twitter.consumerKey}")
    private String _twitterConsumerKey;
    @Value("${twitter.consumerSecret}")
    private String _twitterConsumerSecret;
    @Value("${twitter.callback}")
    private String _twitterCallback;

    @Value("${facebook.appId}")
    private String _facebookAppId;
    @Value("${facebook.appSecret}")
    private String _facebookAppSecret;
    @Value("${facebook.callback}")
    private String _facebookCallback;

    @Bean
    public TwitterFactory twitterFactory() {
        // Build the configuration
        twitter4j.conf.Configuration configuration = new ConfigurationBuilder()
            .setOAuthConsumerKey(_twitterConsumerKey)
            .setOAuthConsumerSecret(_twitterConsumerSecret)
            .build();

        // Instantiate the Twitter object with the configuration
        return new TwitterFactory(configuration);
    }
}