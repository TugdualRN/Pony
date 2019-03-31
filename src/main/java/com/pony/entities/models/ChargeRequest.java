package com.pony.entities.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_shop_product")
public class ChargeRequest {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
    @Column()
	private String name;
	
    @Column()
	private int amount;
	
    @Column()
	private String currency;
    
    @Column(columnDefinition = "TEXT")
	private String description;
	
    @Column()
	private String stripeEmail;
	
    @Column()
    private String stripeToken;

    public ChargeRequest(){
    	super();
    }
    public ChargeRequest(String description, int amount, String currency, String stripeEmail, String stripeToken) {
		super();
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.stripeEmail = stripeEmail;
		this.stripeToken = stripeToken;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStripeEmail() {
		return stripeEmail;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}