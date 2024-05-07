package com.reactive.controller;

import com.reactive.MovieInfoServiceApplication;
import com.reactive.domain.MovieInfo;
import com.reactive.service.MovieInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MovieInfoService movieInfoService;

    public MoviesInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getAllMovieInfos(){
        return movieInfoService.getAllMovieInfos();
    }

    @GetMapping("/movieinfos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){
        return movieInfoService.getMovieInfoById(id);
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo){
        Mono<MovieInfo> movieInfoMono = movieInfoService.addMovieInfo(movieInfo);
        return movieInfoMono.log();
    }

    @PutMapping("/movieinfos/{id}")
    public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo updatedMovieInfo, @PathVariable String id){
        System.out.println("inside put method");
        return movieInfoService.updateMovieInfo(updatedMovieInfo,id);
    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id){
        return movieInfoService.deleteMovieInfo(id);
    }
}
