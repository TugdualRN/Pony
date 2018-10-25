package com.pony.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.models.ChargeRequest;
import com.pony.repositories.ChargeRequestRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
	private String _secretKey = "sk_test_fs1YEOVU6GNmmWAwS3QblWcy";

	private final ChargeRequestRepository _chargeRequestRepo;
	
	@Autowired
	public StripeService(ChargeRequestRepository chargeRequestRepo) {
		this._chargeRequestRepo = chargeRequestRepo;
		
		Stripe.apiKey = _secretKey;
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
