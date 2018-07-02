/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gotug
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"", "/", "/home"})
    public ModelAndView home(Model model) {

        return new ModelAndView("home");
    }
}