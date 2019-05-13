package com.pony.controllers;

import com.pony.business.services.NewsService;
import com.pony.business.services.UserService;
import com.pony.entities.models.News;
import com.pony.entities.models.User;
import com.pony.views.viewmodels.NewsViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class NewsController {

	private NewsService _newsService;
	private UserService _userService;

	@Autowired
	public NewsController(NewsService newsService, UserService userService) {
		_newsService = newsService;
		_userService = userService;
	}

//	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/create-news" })
//	@GetMapping(value = { "/create-news" })
	public ModelAndView createNews() {

		return new ModelAndView("news/contenttools").addObject("newsViewModel", new NewsViewModel());
	}

	@RequestMapping(value = { "/news/{slug}" })
	public ModelAndView displayNews(@PathVariable String slug) {

		News news = _newsService.findBySlug(slug);
		return new ModelAndView("news/displayNews").addObject("news", news);
	}

	@RequestMapping(value = { "/news" })
	public ModelAndView news(Model model) {
		model.addAttribute("newsList", _newsService.findByLangOrderByIdDesc(LocaleContextHolder.getLocale().toLanguageTag()));
		return new ModelAndView("news/news").addObject("news");
	}
	//	, consumes = {"application/x-www-form-urlencoded" }
	@RequestMapping(value = "/create-news", method = RequestMethod.POST, consumes = {"multipart/form-data" })
//	@PostMapping(value = "/create-news", consumes = {"multipart/form-data" })
	public ModelAndView addNews(@Valid @RequestBody @ModelAttribute NewsViewModel viewModel, BindingResult bindingResult) {

		System.out.println("*********************************");
		System.out.println(viewModel);
		System.out.println(bindingResult);
		System.out.println("*********************************");
		if (bindingResult.hasErrors()){
			return new ModelAndView("redirect:create-news");
		}
		//return new ModelAndView("redirect:create-news");
		News news = new News();
		news.setContent(_newsService.formatContent(viewModel.getContent()));
		String title = viewModel.getTitle();
		news.setTitle(title);
		news.setDescription(viewModel.getDescription());
		news.setImg(viewModel.getImg());
		news.setLang(LocaleContextHolder.getLocale().toLanguageTag());
		// get user in session
		Object userConnected = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userConnected.toString());
		// test if user
		User user = _userService.findByMail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		_newsService.createNews(news, user);
		return new ModelAndView("redirect:home");
	}
}