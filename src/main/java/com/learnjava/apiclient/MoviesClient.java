package com.learnjava.apiclient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import com.learnjava.util.CommonUtil;

public class MoviesClient {
	private final WebClient webClient;

	public MoviesClient(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	public Movie retrieveMovie(Long movieInfoId) {
		CommonUtil.startTimer();
		MovieInfo movieInfo = invokeMovieInfoService(movieInfoId);
		List<Review> reviewList = invokeReviewService(movieInfoId);
		CommonUtil.timeTaken();
		return new Movie(movieInfo, reviewList);
	}

	public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {
		CommonUtil.startTimer();
		CompletableFuture<MovieInfo> movieInfo = CompletableFuture
				.supplyAsync(() -> invokeMovieInfoService(movieInfoId));
		CompletableFuture<List<Review>> reviews = CompletableFuture.supplyAsync(() -> invokeReviewService(movieInfoId));
		CommonUtil.timeTaken();
		return movieInfo.thenCombine(reviews, (movie, revs) -> new Movie(movie, revs));
	}

	private List<Review> invokeReviewService(Long movieInfoId) {
		String reviewUrl = UriComponentsBuilder.fromUriString("/v1/reviews").queryParam("movieInfoId", movieInfoId)
				.buildAndExpand().toUriString();
		return webClient.get().uri(reviewUrl).retrieve().bodyToFlux(Review.class).collectList().block();
	}

	public List<Movie> retrieveMovies(List<Long> movieInfoIds) {
		return movieInfoIds.stream().map(movieInfoId -> retrieveMovie(movieInfoId)).collect(Collectors.toList());
	}

	public List<Movie> retrieveMovieList_CF(List<Long> moviesInfoIds) {
		List<CompletableFuture<Movie>> moviesCFList = moviesInfoIds.stream().map(this::retrieveMovie_CF)
				.collect(Collectors.toList());
		return moviesCFList.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	
	public List<Movie> retrieveMovieList_CF_allOf(List<Long> moviesInfoIds) {
		List<CompletableFuture<Movie>> moviesCFList = moviesInfoIds.stream().map(this::retrieveMovie_CF)
				.collect(Collectors.toList());
		CompletableFuture<Void> cfAllOf = CompletableFuture
				.allOf(moviesCFList.toArray(new CompletableFuture[moviesCFList.size()]));

		return cfAllOf.thenApply(v -> moviesCFList.stream().map(CompletableFuture::join).collect(Collectors.toList()))
				.join();
	}
	

	private MovieInfo invokeMovieInfoService(Long movieInfoId) {
		String moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";
		return webClient.get().uri(moviesInfoUrlPath, movieInfoId).retrieve().bodyToMono(MovieInfo.class).block();
	}
}
