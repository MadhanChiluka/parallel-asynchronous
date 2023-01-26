package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

public class ProductServiceUsingCompletableFuture {
	private ProductInfoService productInfoService;
	private ReviewService reviewService;
	private InventoryService inventoryService;

	public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
	}

	public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,
			InventoryService inventoryService) {
		super();
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
		this.inventoryService = inventoryService;
	}

	public Product retrieveProductDetails(String productId) {
		stopWatch.start();

		CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
				.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

		CompletableFuture<Review> cfReview = CompletableFuture
				.supplyAsync(() -> reviewService.retrieveReviews(productId));

		Product product = cfProductInfo
				.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review)).join();

		log("Total Time Taken : " + stopWatch.getTime());
		return product;
	}

	public Product retrieveProductDetailsWithInventory(String productId) {
		stopWatch.start();

		CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
				.supplyAsync(() -> productInfoService.retrieveProductInfo(productId)).thenApply(productInfo -> {
					productInfo.setProductOptions(updateInventory(productInfo));
					return productInfo;
				});

		CompletableFuture<Review> cfReview = CompletableFuture
				.supplyAsync(() -> reviewService.retrieveReviews(productId));

		Product product = cfProductInfo
				.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review)).join();

		log("Total Time Taken : " + stopWatch.getTime());
		return product;
	}
	
	
	public Product retrieveProductDetailsWithInventory_approach2(String productId) {
		stopWatch.start();

		CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
				.supplyAsync(() -> productInfoService.retrieveProductInfo(productId)).thenApply(productInfo -> {
					productInfo.setProductOptions(updateInventory_approach2(productInfo));
					return productInfo;
				});

		CompletableFuture<Review> cfReview = CompletableFuture
				.supplyAsync(() -> reviewService.retrieveReviews(productId));

		Product product = cfProductInfo
				.thenCombine(cfReview, (productInfo, review) -> new Product(productId, productInfo, review)).join();

		log("Total Time Taken : " + stopWatch.getTime());
		return product;
	}

	private List<ProductOption> updateInventory(ProductInfo productInfo) {
		return productInfo.getProductOptions().stream().map(productOption -> {
			Inventory inventory = inventoryService.retrieveInventory(productOption);
			productOption.setInventory(inventory);
			return productOption;
		}).collect(Collectors.toList());

	}

	private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
		List<CompletableFuture<ProductOption>> ProductOptionList = productInfo.getProductOptions().stream()
				.map(productOption -> {
					return CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
							.thenApply(inventory -> {
								productOption.setInventory(inventory);
								return productOption;
							});
				}).collect(Collectors.toList());
		return ProductOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());

	}

	public CompletableFuture<Product> retrieveProductDetails_apprach2(String productId) {

		CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
				.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

		CompletableFuture<Review> cfReview = CompletableFuture
				.supplyAsync(() -> reviewService.retrieveReviews(productId));

		return cfProductInfo.thenCombine(cfReview,
				(productInfo, review) -> new Product(productId, productInfo, review));

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
