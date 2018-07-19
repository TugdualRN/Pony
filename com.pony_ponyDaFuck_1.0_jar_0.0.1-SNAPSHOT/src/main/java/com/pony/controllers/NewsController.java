package com.pony.controllers;


import javax.validation.Valid;

import com.pony.models.News;
import com.pony.services.NewsService;
import com.pony.viewmodels.NewsViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {

	private NewsService _newsService;

	@Autowired
	public NewsController(NewsService newsService) {
		_newsService = newsService;
	}

	@RequestMapping(value = { "/create-news" })
	public ModelAndView createNews() {

		return new ModelAndView("news/contenttools").addObject("newsViewModel", new NewsViewModel());
	}

	@RequestMapping(value = { "/news/{slug}" })
	public ModelAndView displayNews(@PathVariable String slug) {

		News news = _newsService.findBySlug(slug);
		return new ModelAndView("news/displayNews").addObject("news", news);
	}
	@RequestMapping(value = "/create-news", method = RequestMethod.POST, consumes = {
			"application/x-www-form-urlencoded" })
	public ModelAndView addNews(@Valid @RequestBody @ModelAttribute NewsViewModel viewModel,
			BindingResult bindingResult) {

		News news = new News();
//		System.out.println(viewModel.getContent().substring(1, viewModel.getContent().length()-1));
		news.setContent(_newsService.formatContent(viewModel.getContent()));
		String title = viewModel.getTitle();
		news.setTitle(title);
		_newsService.createNews(news);
		return new ModelAndView("redirect:home");

	}

}
