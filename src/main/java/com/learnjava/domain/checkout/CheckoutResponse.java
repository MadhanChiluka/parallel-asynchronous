package com.learnjava.domain.checkout;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CheckoutResponse {
	CheckoutStatus checkoutStatus;
	List<CartItem> errorList = new ArrayList<>();

	public CheckoutResponse(CheckoutStatus checkoutStatus) {
		this.checkoutStatus = checkoutStatus;
	}

	public CheckoutResponse(CheckoutStatus checkoutStatus, List<CartItem> errorList) {
		this.checkoutStatus = checkoutStatus;
		this.errorList = errorList;
	}

}
