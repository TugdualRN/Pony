package com.pony.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pony.business.services.NewsService;

@Controller
public class HomeController {

	private NewsService _newsService;

	@Autowired
	public HomeController(NewsService newsService) {
		_newsService = newsService;
	}
	@RequestMapping(value = {"", "/", "/home"})
	public String home(Model model) {

		model.addAttribute("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));

		return "home";
//		return new ModelAndView("home")
//				.addObject("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));
	}
}