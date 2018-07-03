package com.pony.controllers;

import javax.validation.Valid;

import com.pony.models.News;
import com.pony.models.User;
import com.pony.services.NewsService;
import com.pony.services.UserService;
import com.pony.viewmodels.NewsViewModel;
import com.pony.viewmodels.RegisterViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @RequestMapping(value = {"/create-news"})
    public ModelAndView createNews(Model model) {

        return new ModelAndView("news/contenttools").addObject("newsViewModel", new NewsViewModel());
    }
    @RequestMapping(value = "/create-news", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView addNews(@Valid @RequestBody @ModelAttribute NewsViewModel viewModel, BindingResult bindingResult) {
        
    	News news = new News();
    	news.setContent(viewModel.getContent());
    	news.setTitle(viewModel.getTitle());
    	
    	try {
    		_newsService.insert(news);
    	} catch (Exception e) {
          System.out.println("\n FOUND AN APPLICATION EXCEPTION \n");
          System.out.println(e);
      }
    	  return new ModelAndView("home").addObject("newsList", _newsService.findAll());
        
    }
	
}
