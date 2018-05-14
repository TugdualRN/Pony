/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Gotug
 */
@Controller
public class HomeController {
@Autowired
private MessageSource messageSource;
     @RequestMapping("/home")
     public String home(Model model) {
          
//          model.addAttribute("attribute", "value");
//          System.out.println("====================== HomeController ======================");

          return "home";
     }

}
