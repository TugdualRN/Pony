///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.pony.config;
//
//import org.springframework.context.annotation.Configuration;
//
///**
// *
// * @author Gotug
// */
//
//@Configuration
//@EnableSocial
//public class SocialConfig implements SocialConfigurer {
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//        connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
//            environment.getProperty("spring.social.facebook.appId"),
//            environment.getProperty("spring.social.facebook.appSecret")));
//        ....
//        connectionFactoryConfigurer.addConnectionFactory(new LiveConnectionFactory(
//            environment.getProperty("spring.social.live.appId"),
//            environment.getProperty("spring.social.live.appSecret")));
//    }