package com.pony.controllers;

import javax.validation.Valid;

import com.pony.models.User;
import com.pony.services.UserService;
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


    @RequestMapping(value = {"/contenttool"})
    public ModelAndView home(Model model) {

        return new ModelAndView("contenttools");
    }
	
}
