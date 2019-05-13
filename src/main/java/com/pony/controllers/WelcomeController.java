package com.pony.controllers;

import com.pony.business.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    private NewsService _newsService;

    @Autowired
    public WelcomeController(NewsService newsService) {
        _newsService = newsService;
    }
    
    @RequestMapping(value = {"/welcome"})
    public String welcome(Model model) {

        model.addAttribute("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));

        return "welcome";}

}