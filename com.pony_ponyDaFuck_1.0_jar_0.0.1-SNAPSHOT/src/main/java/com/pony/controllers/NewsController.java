package com.pony.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.github.slugify.Slugify;
import com.pony.models.News;
import com.pony.models.User;
import com.pony.services.NewsService;
import com.pony.services.UserService;
import com.pony.viewmodels.NewsViewModel;
import com.pony.viewmodels.RegisterViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		news.setContent(viewModel.getContent().substring(1, viewModel.getContent().length()-1).replace("\\", ""));
		String title = viewModel.getTitle();
		news.setTitle(title);
		_newsService.createNews(news);
		return new ModelAndView("redirect:home");

	}

}
