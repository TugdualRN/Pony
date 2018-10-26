package com.pony.controllers;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    
    /**
     * Retrieve Connected User Email Address
     * 
     * @return Connected User Email Address
     */
    public String getConnectedUserMail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}