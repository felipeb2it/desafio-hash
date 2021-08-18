package com.desafio.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total_amount", "total_amount_with_discount", "total_discount", "products" })
public class CheckoutResponseModel {

	@JsonProperty("total_amount")
	private BigDecimal totalAmount;
	
	@JsonProperty("total_amount_with_discount")
	private BigDecimal totalAmoutWithDiscount;
	
	@JsonProperty("total_discount")
	private BigDecimal totalDiscount;
	
	private List<ProdutoResponseModel> products;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalAmoutWithDiscount() {
		return totalAmoutWithDiscount;
	}
	public void setTotalAmoutWithDiscount(BigDecimal totalAmoutWithDiscount) {
		this.totalAmoutWithDiscount = totalAmoutWithDiscount;
	}
	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	public List<ProdutoResponseModel> getProducts() {
		return products;
	}

	public void setProducts(List<ProdutoResponseModel> products) {
		this.products = products;
	}
	
}
