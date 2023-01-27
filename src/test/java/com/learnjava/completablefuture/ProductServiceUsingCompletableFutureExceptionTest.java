package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {
	@Mock
	ProductInfoService pis;
	
	@Mock
	ReviewService ris;
	
	@Mock
	InventoryService is;
	
	@InjectMocks
	ProductServiceUsingCompletableFuture pscf;
	

	@Test
	void retrieveProductDetailsWithInventory_approach2() {
		//given
		String productId = "ABC123";
		
		//when
		when(pis.retrieveProductInfo(any())).thenCallRealMethod();
		when(ris.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occurred"));
		when(is.retrieveInventory(any())).thenCallRealMethod();
		
		Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
		
		//then
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product.getProductInfo().getProductOptions().forEach(productInfo -> {
			assertNotNull(productInfo.getInventory());
		});
		assertNotNull(product.getReview());
		assertEquals(0, product.getReview().getNoOfReviews());
	}
	
	
	@Test
	void retrieveProductDetailsWithInventory_productInfoServiceError() {
		//given
		String productId = "ABC123";
		
		//when
		when(pis.retrieveProductInfo(any())).thenCallRealMethod();
		when(ris.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occurred"));
		
		
		//then
		Assertions.assertThrows(RuntimeException.class, () -> pscf.retrieveProductDetailsWithInventory_approach2(productId));
	}

}
