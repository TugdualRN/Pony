package com.pony.services;

import twitter4j.Twitter;
import facebook4j.Facebook;

//import twitter4j.Twitter;

public interface SocialService {

    public Twitter getTwitter();

    public Facebook getFacebook();
}