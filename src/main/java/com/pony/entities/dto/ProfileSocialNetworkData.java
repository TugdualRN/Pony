package com.pony.entities.dto;

import twitter4j.Status;
import twitter4j.User;

import java.util.List;

public class ProfileSocialNetworkData {

    private User user;
    private List<Status> tweets;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Status> getTweets() {
        return this.tweets;
    }

    public void setTweets(List<Status> tweets) {
        this.tweets = tweets;
    }
}