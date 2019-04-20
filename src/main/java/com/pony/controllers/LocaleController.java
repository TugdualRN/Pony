package com.pony.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LocaleController {

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String getLocalePage() {
        return "locale";
    }
}