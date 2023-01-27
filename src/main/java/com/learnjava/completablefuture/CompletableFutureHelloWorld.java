package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

public class CompletableFutureHelloWorld {

	HelloWorldService hws;

	public CompletableFutureHelloWorld(HelloWorldService hws) {
		this.hws = hws;
	}

	public CompletableFuture<String> helloWorld() {
		return CompletableFuture.supplyAsync(hws::helloWorld).thenApply(result -> result.toUpperCase());
	}

	public String helloWorld_approach1() {
		String hello = hws.hello();
		String world = hws.world();
		return hello + world;
	}

	public String helloWorld_multiple_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

		String helloWorld = hello.thenCombine(world, (h, w) -> h + w).thenApply(String::toUpperCase).join();
		timeTaken();
		return helloWorld;
	}

	public String helloWorld_3_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello.thenCombine(world, (h, w) -> h + w)
				.thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr).thenApply(String::toUpperCase).join();

		timeTaken();
		return helloWorld;
	}

	public String helloWorld_3_async_calls_log() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello.thenCombine(world, (h, w) -> {
			log("thenCombine h/w");
			return h + w;
		}).thenCombine(hiCompletableFuture, (prev, curr) -> {
			log("thenCombine prev/curr");
			return prev + curr;
		}).thenApply(s -> {
			log("thenApply");
			return s.toUpperCase();
		}).join();

		timeTaken();
		return helloWorld;
	}
	
	public String helloWorld_3_async_calls_log_async() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello.thenCombineAsync(world, (h, w) -> {
			log("thenCombine h/w");
			return h + w;
		}).thenCombineAsync(hiCompletableFuture, (prev, curr) -> {
			log("thenCombine prev/curr");
			return prev + curr;
		}).thenApplyAsync(s -> {
			log("thenApply");
			return s.toUpperCase();
		}).join();

		timeTaken();
		return helloWorld;
	}
	
	public String helloWorld_3_async_calls_custom_threadPool() {
		startTimer();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		}, executorService);

		String helloWorld = hello.thenCombine(world, (h, w) -> {
			log("thenCombine h/w");
			return h + w;
		}).thenCombine(hiCompletableFuture, (prev, curr) -> {
			log("thenCombine prev/curr");
			return prev + curr;
		}).thenApply(s -> {
			log("thenApply");
			return s.toUpperCase();
		}).join();

		timeTaken();
		return helloWorld;
	}
	
	public String helloWorld_3_async_calls_custom_threadPool_async() {
		startTimer();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		}, executorService);

		String helloWorld = hello.thenCombineAsync(world, (h, w) -> {
			log("thenCombine h/w");
			return h + w;
		}, executorService).thenCombineAsync(hiCompletableFuture, (prev, curr) -> {
			log("thenCombine prev/curr");
			return prev + curr;
		}, executorService).thenApplyAsync(s -> {
			log("thenApply");
			return s.toUpperCase();
		}, executorService).join();

		timeTaken();
		return helloWorld;
	}

	public CompletableFuture<String> helloWorld_thenCompose() {
		return CompletableFuture.supplyAsync(hws::hello).thenCompose(hws::worldFuture)
				.thenApply(result -> result.toUpperCase());
	}

	public static void main(String[] args) {
		HelloWorldService hws = new HelloWorldService();
		CompletableFuture.supplyAsync(() -> hws.helloWorld()).thenApply(result -> result.toUpperCase())
				.thenAccept(result -> LoggerUtil.log("result : " + result)).join();

		LoggerUtil.log("End of Main Thread");

	}

}
