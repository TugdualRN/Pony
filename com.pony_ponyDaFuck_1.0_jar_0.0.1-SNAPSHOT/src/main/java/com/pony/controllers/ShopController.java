package com.pony.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pony.models.ChargeRequest;
import com.pony.repositories.ChargeRequestRepository;
import com.pony.services.NewsService;
import com.pony.services.StripeService;
import com.pony.services.UserService;

@Controller
public class ShopController {

	private  StripeService _stripeService;
	@Autowired
	public ShopController(StripeService stripeService) {
		_stripeService = stripeService;
	}
	@RequestMapping(value = { "/shop" })
	public ModelAndView displayShop() {

		ChargeRequest chargeRequest = new ChargeRequest();
		// TODO get all chargeRequest
		List<ChargeRequest> toto = _stripeService.findAll();
		for(ChargeRequest titi: toto ){
			System.out.println(titi.getDescription());
		}
	
		return new ModelAndView("shop/Shop").addObject("offerList", toto);
	}
}
