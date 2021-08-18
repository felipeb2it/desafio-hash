package com.desafio.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonProductModel {
	
	private Long id;
	private String description;
	private String title;
	private BigDecimal amount;
	
	@JsonProperty("is_gift")
	private boolean gift;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public boolean isGift() {
		return gift;
	}
	public void setGift(boolean gift) {
		this.gift = gift;
	}
	
	
	
}
