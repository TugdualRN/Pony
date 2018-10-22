package com.pony.controllers;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Source;
import com.stripe.net.RequestOptions;

public class StripeExample {

    public static void main(String[] args) {
        Stripe.apiKey = "sk_test_fs1YEOVU6GNmmWAwS3QblWcy";

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        
        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("type", "ach_credit_transfer");
        sourceParams.put("currency", "usd");
        Map<String, Long> metadata = new HashMap<String, Long>();
        metadata.put("user", 1l);
        sourceParams.put("metadata", metadata);
        Map<String, Object> ownerParams = new HashMap<String, Object>();
        ownerParams.put("email", "ponydafuk@gmail.com");
        sourceParams.put("owner", ownerParams);
        try {
			Source source = Source.create(sourceParams);
			System.out.println(source.toJson());
			JsonElement jelement = new JsonParser().parse(source.toJson());
		    JsonObject  jobject = jelement.getAsJsonObject();

			String result = jobject.get("id").getAsString();
			System.out.println(result);
			chargeMap.put("source", "tok_visa");
		} catch (StripeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
         // obtained via Stripe.js

        try {
        	
        	Charge charge = Charge.create(chargeMap);
            
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
    
   
}
