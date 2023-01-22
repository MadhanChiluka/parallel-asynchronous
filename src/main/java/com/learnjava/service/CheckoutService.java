package com.learnjava.service;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.LoggerUtil;

public class CheckoutService {
	private PriceValidatorService priceValidatorService;

	public CheckoutService(PriceValidatorService priceValidatorService) {
		this.priceValidatorService = priceValidatorService;
	}

	public CheckoutResponse checkout(Cart cart) {
		startTimer();
		List<CartItem> priceValidationList = cart.getCartItemList().stream().parallel().map(cartItem -> {
			boolean isPriceItem = this.priceValidatorService.isCartItemInvalid(cartItem);
			cartItem.setExpired(isPriceItem);
			return cartItem;
		}).filter(CartItem::isExpired).collect(Collectors.toList());

		timeTaken();
//		double finalPrice = calculateFinalPrice(cart);
		double finalPrice = calculateFinalPrice_reduce(cart);
		LoggerUtil.log("Checkout complete and the final price " + finalPrice);
		if (priceValidationList.isEmpty()) {
			return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
		}
		return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);

	}

	private double calculateFinalPrice(Cart cart) {
		return cart.getCartItemList().parallelStream()
				.map(item -> item.getQuantity() * item.getRate())
				.mapToDouble(Double::doubleValue)
				.sum();
	}
	
	private double calculateFinalPrice_reduce(Cart cart) {
		return cart.getCartItemList().parallelStream()
				.map(item -> item.getQuantity() * item.getRate())
				.reduce(0.0, Double::sum);
	}
}
