package com.pony.controllers;

import com.pony.business.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChargeController {
 
    @Autowired
    private StripeService paymentsService;
 
    @RequestMapping(value = "/charge", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded" })
//    @PostMapping("/charge")
    public ModelAndView charge(HttpServletRequest request) throws StripeException
    {
    	String token = request.getParameter("stripeToken");
//    	request.
    	Long idProduct = Long.parseLong(request.getParameter("id"));

        Charge charge = paymentsService.charge(idProduct, token);
//        System.out.println(charge);
        return new ModelAndView("shop/result").addObject("charge", charge);
    }
 
    @ExceptionHandler(StripeException.class)
    public ModelAndView handleError(StripeException ex) {
     String error = ex.getMessage();
   
        return new ModelAndView("shop/result").addObject("error", error);
    }
}