package com.pony.controllers;

import com.pony.models.User;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    
    protected long getAuthenticatedUserId() {
        User user = this.getUser();
        if (user != null) {
            return user.getId();
        }

        return -1;
    }

    protected String getAuthenticatedUserName() {
        User user = this.getUser();
        if (user != null) {
            return user.getUserName();
        }

        return "";
    }

    protected String getAuthenticatedUserMail() {
        User user = this.getUser();
        if (user != null) {
            return user.getMail();
        }

        return "";
    }

    private User getUser() {
        try {
            return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch(ClassCastException exception) {
            return null;
        }
    }
}