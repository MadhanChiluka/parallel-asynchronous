package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {
	
	@Mock
	private HelloWorldService hws = mock(HelloWorldService.class);
	
	@InjectMocks
	CompletableFutureHelloWorldException cfhwe;

	@Test
	void testHelloWorld_3_async_calls() {
		//given
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
		when(hws.world()).thenCallRealMethod();
		
		//when
		String result = cfhwe.helloWorld_3_async_calls();
		
		//then world! Hi CompletableFuture
		assertEquals(" WORLD! HI COMPLETABLEFUTURE", result);
	}

	
	@Test
	void helloWorld_3_async_calls_2() {
		//given
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
		when(hws.world()).thenThrow(new RuntimeException("Exception Occurred"));
		
		//when
		String result = cfhwe.helloWorld_3_async_calls();
		
		//then world! Hi CompletableFuture
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
		
	@Test
	void helloWorld_3_async_calls_3() {
		//given
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		//when
		String result = cfhwe.helloWorld_3_async_calls();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_exceptionally() {
		//given
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		//when
		String result = cfhwe.helloWorld_3_async_calls_exceptionally();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_exceptionally_2() {
		//given
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
		when(hws.world()).thenThrow(new RuntimeException("Exception Occurred"));
		//when
		String result = cfhwe.helloWorld_3_async_calls_exceptionally();
		
		//then world! Hi CompletableFuture
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_whenComplete() {
		//given
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		//when
		String result = cfhwe.helloWorld_3_async_calls_whenComplete();
		
		//then world! Hi CompletableFuture
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
	@Test
	void helloWorld_3_async_calls_whenComplete_2() {
		//given
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
		when(hws.world()).thenThrow(new RuntimeException("Exception Occurred"));
		//when
		
		//when
		String result = cfhwe.helloWorld_3_async_calls_whenComplete();
		
		//then world! Hi CompletableFuture
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
	

}
