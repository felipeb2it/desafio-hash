package com.desafio.service;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.desafio.model.CheckoutModel;
import com.desafio.model.ProdutoModel;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
@TestInstance(Lifecycle.PER_CLASS)
public class HashServiceTest {
	
    @Inject
    @Client("http://localhost:8080")
    RxHttpClient client;
	
	CheckoutModel checkoutModel;
	
	@BeforeAll
	void init() {
		checkoutModel = new CheckoutModel();
		ProdutoModel produto = new ProdutoModel();
		produto.setId(1L);
		produto.setQuantity(2);
		checkoutModel.setProducts(Arrays.asList(produto));
	}
	
	@Test
	void testCheckoutSuccess() {
		checkoutModel.getProducts().get(0).setQuantity(1);
		checkoutModel.getProducts().get(0).setId(1L);
		HttpResponse<Object> response = client.toBlocking().exchange(
    			HttpRequest.POST("/checkout", checkoutModel)
    				.accept(MediaType.APPLICATION_JSON), Object.class);
		
		Assertions.assertEquals(response.status(), HttpStatus.OK);
	}
	
	@Test
	void testInvalidId() {
		checkoutModel.getProducts().get(0).setId(-10L);
		HttpClientResponseException requestException = Assertions.assertThrows(HttpClientResponseException.class, () ->
		client.toBlocking().exchange(
    			HttpRequest.POST("/checkout", checkoutModel)
    				.accept(MediaType.APPLICATION_JSON), Object.class)
		);
		Assertions.assertEquals(requestException.getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void testInvalidQuantity() {
		checkoutModel.getProducts().get(0).setQuantity(-10);
		HttpClientResponseException requestException = Assertions.assertThrows(HttpClientResponseException.class, () ->
		client.toBlocking().exchange(
    			HttpRequest.POST("/checkout", checkoutModel)
    				.accept(MediaType.APPLICATION_JSON), Object.class)
		);
		Assertions.assertEquals(requestException.getStatus(), HttpStatus.BAD_REQUEST);
	}
	
}
