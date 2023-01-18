package com.learnjava.domain.checkout;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private Integer cardId;
	private List<CartItem> cartItemList;
}
