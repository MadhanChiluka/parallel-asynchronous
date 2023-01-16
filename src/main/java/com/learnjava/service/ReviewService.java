package com.learnjava.service;

import static com.learnjava.util.CommonUtil.delay;

import com.learnjava.domain.Review;

public class ReviewService {
	public Review retrieveReviews(String productId) {
		delay(1000);
		return new Review(200, 4.5);
	}
}
