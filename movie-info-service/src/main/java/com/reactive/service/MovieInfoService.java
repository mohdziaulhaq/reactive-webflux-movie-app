package com.reactive.service;

import com.reactive.domain.MovieInfo;
import com.reactive.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    private MovieInfoRepository movieInfoRepository;
    public MovieInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }
}
