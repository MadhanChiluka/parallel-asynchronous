package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.concurrent.CompletableFuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {
	HelloWorldService hws;

	public CompletableFutureHelloWorldException(HelloWorldService hws) {
		this.hws = hws;
	}

	public String helloWorld_3_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello.handle((res, e) -> {
			log("res : " + res);
			if (e != null) {
				log("Exception Occured : " + e.getMessage());
				return "";
			}
			return res;
		}).thenCombine(world, (h, w) -> h + w).handle((res, e) -> {
			log("res : " + res);
			if (e != null) {
				log("Exception after world is : " + e.getMessage());
				return "";
			}
			return res;
		}).thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr).thenApply(String::toUpperCase).join();

		timeTaken();
		return helloWorld;
	}
	
	public String helloWorld_3_async_calls_exceptionally() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello
				.exceptionally((e) -> {
					log("Exception Occured : " + e.getMessage());
					return "";
				}).thenCombine(world, (h, w) -> h + w)
					.exceptionally((e) -> {
						log("Exception after world is : " + e.getMessage());
						return "";
					}).thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr).thenApply(String::toUpperCase).join();

		timeTaken();
		return helloWorld;
	}
	
	public String helloWorld_3_async_calls_whenComplete() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			CommonUtil.delay(100);
			return " Hi CompletableFuture";
		});

		String helloWorld = hello.whenComplete((res, e) -> {
			log("res : " + res);
			if (e != null) {
				log("Exception Occured : " + e.getMessage());
			}
		}).thenCombine(world, (h, w) -> h + w).whenComplete((res, e) -> {
			log("res : " + res);
			if (e != null) {
				log("Exception after world is : " + e.getMessage());
			}
		}).exceptionally((e) -> {
			log("Exception Occured after thenCombine is : " + e.getMessage());
			return "";
		})
				.thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr).thenApply(String::toUpperCase).join();

		timeTaken();
		return helloWorld;
	}

}
