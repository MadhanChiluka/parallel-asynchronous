package com.learnjava.apiclient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.learnjava.domain.movie.Movie;

class MoviesClientTest {

	WebClient weClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();

	MoviesClient moviesClient = new MoviesClient(weClient);

	@RepeatedTest(10)
	void retrieveMovie() {
		// given
		Long movieInfoId = 1L;

		// when
		Movie movie = moviesClient.retrieveMovie(movieInfoId);

		System.out.println("Movie is : " + movie);
		assert movie != null;
		assertEquals("Batman Begins", movie.getMovieInfo().getName());
		assert movie.getReviews().size() == 1;
	}

	@RepeatedTest(10)
	void retrieveMovie_CF() {
		// given
		Long movieInfoId = 1L;

		// when
		Movie movie = moviesClient.retrieveMovie_CF(movieInfoId).join();

		System.out.println("Movie is CF : " + movie);
		assert movie != null;
		assertEquals("Batman Begins", movie.getMovieInfo().getName());
		assert movie.getReviews().size() == 1;
	}

	@RepeatedTest(10)
	void retrieveMovies() {
		// given
		List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7l);

		// when
		List<Movie> movies = moviesClient.retrieveMovies(movieInfoId);

		System.out.println("Movie is LIST : " + movies);
		assert movies != null;
		assert movies.size() == 7;
	}
	
	@RepeatedTest(10)
	void retrieveMovieList_CF() {
		// given
		List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7l);

		// when
		List<Movie> movies = moviesClient.retrieveMovieList_CF(movieInfoId);

		System.out.println("Movie is CF LIST : " + movies);
		assert movies != null;
		assert movies.size() == 7;
	}
	
	@RepeatedTest(10)
	void retrieveMovieList_CF_allOf() {
		// given
		List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7l);

		// when
		List<Movie> movies = moviesClient.retrieveMovieList_CF_allOf(movieInfoId);

		System.out.println("Movie is CF LIST : " + movies);
		assert movies != null;
		assert movies.size() == 7;
	}

}
