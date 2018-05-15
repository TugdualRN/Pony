/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Gotug
 */
@Controller
public class LoginController {

     @RequestMapping("/login")
     public String login(Model model) {
//          model.addAttribute("attribute", "value");
//          System.out.println("====================== LoginController ======================");
          return "login";
     }

     // Login form with error
     @RequestMapping("/login-error.html")
     public String loginError(Model model) {
          model.addAttribute("loginError", true);
          return "login";
     }

}
