package com.learnjava.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@NonNull
	private String productId;
	@NonNull
	private ProductInfo productInfo;
	@NonNull
	private Review review;
}
