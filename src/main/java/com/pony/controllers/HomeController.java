package com.pony.controllers;

import com.pony.business.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private NewsService _newsService;

	@Autowired
	public HomeController(NewsService newsService) {
		_newsService = newsService;
	}


	@RequestMapping(value = {"", "/"})
	public String welcome(Model model ) {

		model.addAttribute("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));

		return "fragments/welcome";}


@RequestMapping(value = {"/home"})
	public String home(Model model ) {

		model.addAttribute("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));

		return "home";}

//
//	@RequestMapping(value = "/locale", method = RequestMethod.GET)
//	public String getLocalePage() {
//		return "locale";
//	}
}
