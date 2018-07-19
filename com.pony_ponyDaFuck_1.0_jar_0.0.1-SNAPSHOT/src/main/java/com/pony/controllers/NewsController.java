package com.pony.controllers;

import javax.validation.Valid;

import com.pony.models.News;

import com.pony.models.User;
import com.pony.security.ConnectedUserDetails;
import com.pony.services.NewsService;
import com.pony.services.UserService;
import com.pony.viewmodels.NewsViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private UserService _userService;
	
	@Autowired
	public NewsController(NewsService newsService, UserService userService) {
		_newsService = newsService;
		_userService = userService;
	}
	
	@PreAuthorize("hasRole('WRITER')")
	@RequestMapping(value = { "/create-news" })
	public ModelAndView createNews() {

		return new ModelAndView("news/contenttools").addObject("newsViewModel", new NewsViewModel());
	}

	@RequestMapping(value = { "/news/{slug}" })
	public ModelAndView displayNews(@PathVariable String slug) {

		News news = _newsService.findBySlug(slug);
		return new ModelAndView("news/displayNews").addObject("news", news);
	}
	
	@RequestMapping(value = "/create-news", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded" })
	public ModelAndView addNews(@Valid @RequestBody @ModelAttribute NewsViewModel viewModel,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()){
			return new ModelAndView("redirect:create-news");
		}
		News news = new News();

		news.setContent(_newsService.formatContent(viewModel.getContent()));
		String title = viewModel.getTitle();
		news.setTitle(title);
		// get user in session
		Object userConnected = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// test if user
		User user = _userService.findByMail(((ConnectedUserDetails) userConnected).getUsername());		
		_newsService.createNews(news, user);
		return new ModelAndView("redirect:home");
	}
}
