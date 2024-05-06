package com.reactive.repository;

import com.reactive.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

}
