package com.pony.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pony.entities.models.ChargeRequest;
import com.pony.business.services.StripeService;

@Controller
public class ShopController {

	private  StripeService _stripeService;
	
	@Autowired
	public ShopController(StripeService stripeService) {
		_stripeService = stripeService;
	}

	@RequestMapping(value = { "/shop" })
	public ModelAndView displayShop() {

		//ChargeRequest chargeRequest = new ChargeRequest();
		// {TO DO} get all chargeRequest
		List<ChargeRequest> toto = _stripeService.findAll();
		
		return new ModelAndView("shop/Shop").addObject("offerList", toto);
	}
}
