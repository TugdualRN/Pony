package com.pony.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.servlet.NoHandlerFoundException;

// import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ErrorController {

    private static final Logger _logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serverExceptionHandler(final Throwable throwable) {

        _logger.error("Caught an unhandled Exception: ", throwable);

        return new ModelAndView("error").addObject("exception", throwable);
    }

    // @ExceptionHandler(NoHandlerFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ModelAndView notfoundExceptionHandler(HttpServletRequest request, Exception e)   {
    //     _logger.error("Request: " + request.getRequestURL() + "returned 404", e);
        
    //     return new ModelAndView("error").addObject("exception", e);
    // }
}