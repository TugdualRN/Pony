package com.pony.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ErrorController {

    private static final Logger _logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {

        _logger.error("Caught an unhandled Exception: ", throwable);
        
        model.addAttribute("exception", throwable);

        return "error";
    }
}