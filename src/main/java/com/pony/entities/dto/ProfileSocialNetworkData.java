package com.pony.entities.dto;

import java.util.List;

import twitter4j.Status;
import twitter4j.User;

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