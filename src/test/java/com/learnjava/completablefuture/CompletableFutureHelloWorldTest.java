package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;

class CompletableFutureHelloWorldTest {
	HelloWorldService hws = new HelloWorldService();
	CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

	@Test
	void helloWorld() {
		//given
		
		//when
		CompletableFuture<String> completableFuture = cfhw.helloWorld();
		
		//then
		completableFuture.thenAccept(result -> {
			assertEquals("HELLO WORLD", result);
		})
		.join();
	}
	

	@Test
	void helloWorld_multiple_async_calls() {
		//given
		
		//when
		String helloWorld = cfhw.helloWorld_multiple_async_calls();
		
		//then
		assertEquals("HELLO WORLD!", helloWorld);
	}
	
	@Test
	void helloWorld_3_async_calls() {
		//given
		
		//when
		String helloWorld = cfhw.helloWorld_3_async_calls();
		
		//then
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", helloWorld);
	}
	
	
	@Test
	void helloWorld_3_async_calls_custom_threadPool() {
		
		//when
		String result = cfhw.helloWorld_3_async_calls_custom_threadPool();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_custom_threadPool_async() {
		
		//when
		String result = cfhw.helloWorld_3_async_calls_custom_threadPool_async();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_log() {
		
		//when
		String result = cfhw.helloWorld_3_async_calls_log();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_log_async() {
		
		//when
		String result = cfhw.helloWorld_3_async_calls_log_async();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_thenCompose() {
		CommonUtil.startTimer();
		//given
		
		//when
		CompletableFuture<String> completableFuture = cfhw.helloWorld_thenCompose();
		
		//then
		completableFuture.thenAccept(result -> {
			assertEquals("HELLO WORLD!", result);
		})
		.join();
		CommonUtil.timeTaken();
	}
	
}
