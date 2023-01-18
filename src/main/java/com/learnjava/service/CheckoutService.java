package com.learnjava.service;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

public class CheckoutService {
	private PriceValidatorService priceValidatorService;

	public CheckoutService(PriceValidatorService priceValidatorService) {
		this.priceValidatorService = priceValidatorService;
	}

	public CheckoutResponse checkout(Cart cart) {
		startTimer();
		List<CartItem> priceValidationList = cart.getCartItemList()
				.stream()
				.parallel()
				.map(cartItem -> {
			boolean isPriceItem = this.priceValidatorService.isCartItemInvalid(cartItem);
			cartItem.setExpired(isPriceItem);
			return cartItem;
		}).filter(CartItem::isExpired).collect(Collectors.toList());

		timeTaken();
		if (priceValidationList.isEmpty()) {
			return new CheckoutResponse(CheckoutStatus.SUCCESS);
		}
		return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);

	}
}
