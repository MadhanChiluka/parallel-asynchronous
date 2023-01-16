package com.learnjava.service;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;

public class ProductService {
	private ProductInfoService productInfoService;
	private ReviewService reviewService;

	public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
	}

	public Product retrieveProductDetails(String productId) throws InterruptedException {
		stopWatch.start();

		ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
		ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
		
		Thread productInfoThread = new Thread(productInfoRunnable);
		productInfoThread.start();
		
		Thread reviewThread = new Thread(reviewRunnable);
		reviewThread.start();
		
		productInfoThread.join();
		reviewThread.join();

		ProductInfo productInfo = productInfoRunnable.getProductInfo();
		Review review = reviewRunnable.getReview();
		stopWatch.stop();
		log("Total Time Taken : " + stopWatch.getTime());
		return new Product(productId, productInfo, review);
	}

	public static void main(String[] args) throws InterruptedException {
		ProductInfoService productInfoService = new ProductInfoService();
		ReviewService reviewService = new ReviewService();

		ProductService productService = new ProductService(productInfoService, reviewService);
		String productId = "ABC";
		Product product = productService.retrieveProductDetails(productId);
		log("Product is " + product);

	}

	private class ProductInfoRunnable implements Runnable {

		private String productId;
		private ProductInfo productInfo;

		public ProductInfoRunnable(String productId) {
			this.productId = productId;
		}

		public ProductInfo getProductInfo() {
			return productInfo;
		}

		@Override
		public void run() {
			productInfo = productInfoService.retrieveProductInfo(productId);
		}

	}

	private class ReviewRunnable implements Runnable {

		private String productId;
		private Review review;

		public ReviewRunnable(String productId) {
			this.productId = productId;
		}

		public String getProductId() {
			return productId;
		}

		public Review getReview() {
			return review;
		}

		@Override
		public void run() {
			review = reviewService.retrieveReviews(productId);
		}

	}
}
