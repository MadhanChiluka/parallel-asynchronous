package com.learnjava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;

class CheckoutServiceTest {
	PriceValidatorService priceValidatorService = new PriceValidatorService();
	CheckoutService checkoutService = new CheckoutService(priceValidatorService);

	@Test
	void no_of_cores() {
		System.out.println("No of Cores : " + Runtime.getRuntime().availableProcessors());
	}

	@Test
	void testCheckout_6_items() {
		// given
		Cart cart = DataSet.createCart(6);

		// when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		// then
		assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());

	}
	
	
	@Test
	void testCheckout_15_items() {
		// given
		Cart cart = DataSet.createCart(25);

		// when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		// then
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

	}

}
