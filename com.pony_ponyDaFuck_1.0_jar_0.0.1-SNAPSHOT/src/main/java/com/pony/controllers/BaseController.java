package com.pony.controllers;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    
    public String getConnectedUserMail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // protected long getAuthenticatedUserId() {
    //     User user = this.getUser();
    //     if (user != null) {
    //         return user.getId();
    //     }

    //     return -1;
    // }

    // protected String getAuthenticatedUserName() {
    //     User user = this.getUser();
    //     if (user != null) {
    //         return user.getUserName();
    //     }

    //     return "";
    // }

    // protected String getAuthenticatedUserMail() {
    //     User user = this.getUser();
    //     if (user != null) {
    //         return user.getMail();
    //     }

    //     return "";
    // }

    // public String getConnectedUserMail() {
    //     return SecurityContextHolder.getContext().getAuthentication().getName();
    // }

    // public User getUser() {

    //     try {
    //         return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     } catch(ClassCastException exception) {
    //         return null;
    //     }
    // }
}