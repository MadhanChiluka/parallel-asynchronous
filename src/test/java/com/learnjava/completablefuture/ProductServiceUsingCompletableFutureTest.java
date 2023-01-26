package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

class ProductServiceUsingCompletableFutureTest {
	
	private ProductInfoService pis = new ProductInfoService();
	private ReviewService rs = new ReviewService();
	private InventoryService is= new InventoryService();;
	ProductServiceUsingCompletableFuture pscf =  new ProductServiceUsingCompletableFuture(pis, rs, is);
	

	@Test
	void retrieveProductDetails() {
		
		//given
		String productId = "ABC123";
		
		//when
		Product product = pscf.retrieveProductDetails(productId);
		
		//then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
	}
	
	@Test
	void retrieveProductDetails_apprach2() throws InterruptedException, ExecutionException {
		
		//given
		String productId = "ABC123";
		
		//when
		CompletableFuture<Product> product = pscf.retrieveProductDetails_apprach2(productId);
		
		//then
		assertNotNull(product.get());
		assertTrue(product.get().getProductInfo().getProductOptions().size() > 0);
	}
	
	@Test
	void retrieveProductDetailsWithInventory() {

		// given
		String productId = "ABC123";

		// when
		Product product = pscf.retrieveProductDetailsWithInventory(productId);

		// then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product.getProductInfo().getProductOptions().forEach(productInfo -> {
			assertNotNull(productInfo.getInventory());
		});
		assertNotNull(product.getReview());
	}
	
	@Test
	void retrieveProductDetailsWithInventory_approach2() {

		// given
		String productId = "ABC123";

		// when
		Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);

		// then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product.getProductInfo().getProductOptions().forEach(productInfo -> {
			assertNotNull(productInfo.getInventory());
		});
		assertNotNull(product.getReview());
	}


}
