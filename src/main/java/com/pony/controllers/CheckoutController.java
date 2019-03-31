package com.pony.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CheckoutController {
	 
//	    private String stripePublicKey = ;
	 
	    @RequestMapping("/checkout")
	    public ModelAndView checkout(Model model) {
//	        model.addAttribute("amount", 50 * 100); // in cents
//	        model.addAttribute("stripePublicKey", stripePublicKey);
//	        model.addAttribute("currency", ChargeRequest.Currency.EUR);
//	        
	        return new ModelAndView("shop/result");
	    }
}
