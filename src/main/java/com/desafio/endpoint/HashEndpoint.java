package com.desafio.endpoint;

import javax.inject.Inject;

import com.desafio.model.CheckoutModel;
import com.desafio.service.HashService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller
public class HashEndpoint {
	
	@Inject
	HashService hashService;
	
	@Post(uri = "/checkout")
	public HttpResponse<Object> carrinhoCheckout(@Body CheckoutModel checkoutModel) {
		return HttpResponse.ok(hashService.checkout(checkoutModel));
	}

}
