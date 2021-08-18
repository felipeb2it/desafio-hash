package com.desafio.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desafio.grpc.GrpcClient;
import com.desafio.model.CheckoutModel;
import com.desafio.model.CheckoutResponseModel;
import com.desafio.model.JsonProductModel;
import com.desafio.model.ProdutoModel;
import com.desafio.model.ProdutoResponseModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.runtime.http.scope.RequestScope;

@RequestScope
public class HashService {
	
	private static final Logger LOG = LoggerFactory.getLogger(HashService.class);
	
	private Map<Long, JsonProductModel> mapProduct;
	
	private DecimalFormat df;
	private MonthDay monthDay;
	public static final long GIFT_INDEX = 6;
	public static final int DIA_BLACK_FRIDAY = 26;
	public static final int MES_BLACK_FRIDAY = 11;
	
	private List<JsonProductModel> readProductsJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectReader objectReader = mapper.reader().forType(new TypeReference<List<JsonProductModel>>(){});
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream products = classloader.getResourceAsStream("products.json");
		return objectReader.readValue(products);
	}
	
	@PostConstruct
	public void init() {
		df = new DecimalFormat("0");
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		try {
			List<JsonProductModel> productList = readProductsJson();
			mapProduct = productList.stream()
					.collect(Collectors.toMap(JsonProductModel::getId, Function.identity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		monthDay = MonthDay.of(MES_BLACK_FRIDAY, DIA_BLACK_FRIDAY);
		
	}
	
	public CheckoutResponseModel checkout(CheckoutModel checkout) {
		List<ProdutoModel> listProduto = checkout.getProducts();
		List<ProdutoResponseModel> prodResponse = new ArrayList<>();
		
		BigDecimal totalAmount = new BigDecimal(df.format(0));
		for(ProdutoModel model : listProduto) {
			JsonProductModel json = mapProduct.get(model.getId());
			if(json == null) {
				throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Produto com id " + model.getId() + " não existe na fonte de dados Json!");
			} else if(model.getQuantity() < 1) {
				throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Quantidade de produtos deve ser maior que 0!");
			}
			ProdutoResponseModel produto = new ProdutoResponseModel();
			produto.setQuantity(model.getQuantity());
			produto.setId(model.getId());
			produto.setUnitAmount(json.getAmount());
			totalAmount = json.getAmount().multiply(new BigDecimal(model.getQuantity()));
			float percentage = 0;
			BigDecimal totalAmountWithDiscount = new BigDecimal(df.format(0));
			try {
				percentage = GrpcClient.callDiscount();
				totalAmountWithDiscount = totalAmount.subtract(totalAmount.multiply(new BigDecimal(percentage)));
			} catch (Exception e) {
				LOG.info("Discount service is offline!");
			}
			
			produto.setTotalAmount(totalAmountWithDiscount.intValue() == 0? totalAmount : new BigDecimal(df.format(totalAmountWithDiscount.longValue())));
			produto.setDiscount(new BigDecimal(df.format(totalAmount.multiply(new BigDecimal(percentage)).longValue())));
			prodResponse.add(produto);
			
		}
		
		if(MonthDay.now().equals(monthDay)) {
			LOG.info("It's Black-friday, adding gift!");
			JsonProductModel giftModel = mapProduct.get(GIFT_INDEX);
			
			ProdutoResponseModel gift = new ProdutoResponseModel();
			gift.setQuantity(1);
			gift.setId(giftModel.getId());
			gift.setUnitAmount(new BigDecimal(0));
			gift.setTotalAmount(new BigDecimal(0));
			gift.setDiscount(new BigDecimal(0));
			gift.setGift(true);
			prodResponse.add(gift);
		}
		
		CheckoutResponseModel checkoutResponse = new CheckoutResponseModel();
		checkoutResponse.setTotalDiscount(new BigDecimal(df.format(prodResponse.stream()
				.map(ProdutoResponseModel::getDiscount).reduce(new BigDecimal(0), BigDecimal::add))));
		
		checkoutResponse.setTotalAmoutWithDiscount(new BigDecimal(df.format(prodResponse.stream()
				.map(ProdutoResponseModel::getTotalAmount).reduce(new BigDecimal(0), BigDecimal::add))));
			
		checkoutResponse.setProducts(prodResponse);
		checkoutResponse.setTotalAmount(totalAmount);
		return checkoutResponse;
	}
	
}
