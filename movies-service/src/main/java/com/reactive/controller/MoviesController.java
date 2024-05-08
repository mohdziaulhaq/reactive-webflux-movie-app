package com.reactive.controller;

import com.reactive.client.MoviesInfoRestClient;
import com.reactive.domain.Movie;
import com.reactive.domain.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MoviesController {

    private MoviesInfoRestClient moviesInfoRestClient;

    public MoviesController(MoviesInfoRestClient moviesInfoRestClient) {
        this.moviesInfoRestClient = moviesInfoRestClient;
    }

    @GetMapping("/{id}")
    Mono<Movie> retrieveMovieById(@PathVariable("id") String movieId){
        Review r1 = new Review("1",1L,"Awesome movie",9.0);
        Review r2 = new Review("2",1L,"Awesome movie",9.0);

        var reviewsListMono = Mono.just(List.of(r1,r2));

        return moviesInfoRestClient.retrieveMovieInfo(movieId)// returns Mono<MovieInfo
                .flatMap(movieInfo -> { // we use flatMap, when we are dealing with transformations that deals with  reactive type
          // var reviewsListMono =  reviewsRestClient.retrieveReviews(movieId) //returns Flux<Reviews>
          //             .collectList() // collects all elements emitted by flux into a list and returns a Mono<List<T>>

                    return reviewsListMono.map(reviews ->
                        new Movie(movieInfo, reviews));
                });
    }
    //OP
    /*
    {
    "movieInfo": {
        "movieInfoId": "1",
        "name": "Batman Begins",
        "year": 2005,
        "cast": [
            "Christian Bale",
            "Michael Cane"
        ],
        "release_date": "2005-06-15"
    },
    "reviewList": [
        {
            "reviewId": "1",
            "movieInfoId": 1,
            "moment": "Awesome movie",
            "rating": 9.0
        },
        {
            "reviewId": "2",
            "movieInfoId": 1,
            "moment": "Awesome movie",
            "rating": 9.0
        }
    ]
}
     */
}
