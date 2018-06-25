package com.pony.controllers;

import javax.validation.Valid;

import com.pony.viewmodels.RegisterViewModel;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('USER')")
public class DebugController {

    @RequestMapping("/debug")
    public ModelAndView register(@Valid @RequestBody @ModelAttribute RegisterViewModel viewModel, BindingResult bindingResult) {
        return null;
    }
}