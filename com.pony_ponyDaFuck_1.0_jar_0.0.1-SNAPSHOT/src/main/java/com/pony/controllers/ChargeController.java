package com.pony.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pony.models.ChargeRequest;
import com.pony.business.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Controller
public class ChargeController {
 
    @Autowired
    private StripeService paymentsService;
 
    @RequestMapping(value = "/charge", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded" })
    public ModelAndView charge(ChargeRequest chargeRequest) throws StripeException 
    {
//        chargeRequest.setCurrency(Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        System.out.println(charge);
        return new ModelAndView("shop/result").addObject("charge", charge);
    }
 
    @ExceptionHandler(StripeException.class)
    public ModelAndView handleError(StripeException ex) {
     String error = ex.getMessage();
   
        return new ModelAndView("shop/result").addObject("error", error);
    }
}