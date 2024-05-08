package com.reactive.controller;

import com.reactive.domain.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movies")
public class MoviesController {

    @GetMapping("/{id}")
    Mono<Movie> retrieveMovieById(@PathVariable("id") String movieId){

    }
}
