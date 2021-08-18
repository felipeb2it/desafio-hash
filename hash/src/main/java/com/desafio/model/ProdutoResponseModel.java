package com.desafio.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoResponseModel extends ProdutoModel {
	
	@JsonProperty("unit_amount")
	private BigDecimal unitAmount;
	
	@JsonProperty("total_amount")
	private BigDecimal totalAmount;
	
	@JsonProperty("discount")
	private BigDecimal discount;
	
	@JsonProperty("is_gift")
	private boolean gift;
	
	public BigDecimal getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public boolean isGift() {
		return gift;
	}
	public void setGift(boolean gift) {
		this.gift = gift;
	}
	
}
