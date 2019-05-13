package com.pony.spring.injection;

import facebook4j.FacebookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TwitterFactory;

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
        twitter4j.conf.Configuration configuration = new twitter4j.conf.ConfigurationBuilder()
            .setOAuthConsumerKey(_twitterConsumerKey)
            .setOAuthConsumerSecret(_twitterConsumerSecret)
            .build();

        // Instantiate the Twitter object with the configuration
        return new TwitterFactory(configuration);
    }

    @Bean
    public FacebookFactory facebookFactory() {
        facebook4j.conf.Configuration configuration = new facebook4j.conf.ConfigurationBuilder()
            .setOAuthAppId(_facebookAppId)
            .setOAuthAppSecret(_facebookAppSecret)
            .build();

        return new FacebookFactory(configuration);
    }
}