package com.pony.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    /**
     * Retrieve Connected User Email Address
     * 
     * @return                  Connected User Email Address
     */
    public String getConnectedUserMail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Returns a generic error page for the given message
     * 
     * @param errorMessage      Error message to display
     * 
     * @return                  View
     */
    public ModelAndView returnToErrorPage(String errorMessage) {
        Assert.notNull(errorMessage, "successMessage");

        return new ModelAndView("general/error")
            .addObject("message", errorMessage);
    }

    /**
     *  Returns a generic success page for the given message 
     * 
     * @param successMessage    Success message to display
     *
     * @return                  View
     */
    public ModelAndView returnToSuccessPage(String successMessage) {
        Assert.notNull(successMessage, "successMessage");

        return new ModelAndView("general/success")
            .addObject("message", successMessage);
    }
}