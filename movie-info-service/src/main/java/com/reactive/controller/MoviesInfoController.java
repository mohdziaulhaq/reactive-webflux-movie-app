package com.reactive.controller;

import com.reactive.MovieInfoServiceApplication;
import com.reactive.domain.MovieInfo;
import com.reactive.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MovieInfoService movieInfoService;

    public MoviesInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
        Mono<MovieInfo> movieInfoMono = movieInfoService.addMovieInfo(movieInfo);
        return movieInfoMono.log();
    }
}
