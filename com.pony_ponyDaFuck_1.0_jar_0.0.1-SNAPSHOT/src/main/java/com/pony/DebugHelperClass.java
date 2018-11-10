package com.pony;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

/**
 * Class used for debuging purpose (printing and os on...)
 * 
 * Put every non-shippable things here
 */
public class DebugHelperClass {

    public static void printDebug(AccessToken token, Twitter twitter)
    {
        //System.out.println("REQUEST_TOKEN TOKEN : " + requestToken.getToken());
        System.out.println("ACCESS_TOKEN : " + token.getToken());
        System.out.println("ACCESS_TOKEN SECRET : " + token.getTokenSecret());
        
        System.out.println("Registered USER ID: " + token.getUserId());
        System.out.println("Registered USER NAME: " + token.getScreenName());
        System.out.println("Registered USER TOKEN: " + token.getToken());
        System.out.println("Registered USER SECRET: " + token.getTokenSecret());

        try {
            ResponseList<Status> lastStatus = twitter.getUserTimeline();

            System.out.println("----");
            System.out.println();
    
            for (Status status : lastStatus)
            {
                System.out.println(status.getText());
                System.out.println();
            }
        }
        catch (Exception e) { }
    }
}