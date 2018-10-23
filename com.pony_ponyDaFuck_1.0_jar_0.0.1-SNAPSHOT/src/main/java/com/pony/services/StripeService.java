package com.pony.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.models.ChargeRequest;
import com.pony.models.News;
import com.pony.repositories.ChargeRequestRepository;
import com.pony.repositories.NewsRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;

@Service
public class StripeService {

	private String secretKey = "sk_test_fs1YEOVU6GNmmWAwS3QblWcy";

	private  ChargeRequestRepository _chargeRequestRepo;
	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}
	 @Autowired
	    public StripeService(ChargeRequestRepository chargeRequestRepo) {
	        this._chargeRequestRepo = chargeRequestRepo;
	    }
		
	public Charge charge(ChargeRequest chargeRequest) throws StripeException{
		
			Map<String, Object> chargeParams = new HashMap<>();
			chargeParams.put("amount", chargeRequest.getAmount());
			chargeParams.put("currency", chargeRequest.getCurrency());
			chargeParams.put("description", chargeRequest.getDescription());
			chargeParams.put("source", chargeRequest.getStripeToken());

			return Charge.create(chargeParams);
	}
//	@Override
    @Transactional(readOnly = true)
    public List<ChargeRequest> findAll() {
        return _chargeRequestRepo.findAllByOrderByAmountAsc();
    }
}
