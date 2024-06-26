package com.reactive.controller;

import com.reactive.domain.MovieInfo;
import com.reactive.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntgTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    MovieInfoRepository movieInfoRepository;

    static String MOVIES_INFO_URL = "/v1/movieinfos";

    @BeforeEach
    void setUp(){
        var movieinfos = List.of(new MovieInfo(null, "Batman Begins", 2005, List.of("Christian Bale", "Micheal Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight", 2008, List.of("Christian Bale", "Heath Ledger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "The Dark Knight Rises", 2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieinfos)
                .blockLast();
    }

    @AfterEach
    void tearDown(){
    movieInfoRepository.deleteAll().block();
    }
    @Test
    void addMovieInfo() {

        var movieInfo = new MovieInfo(null, "Batman Begins1", 2005, List.of("Christian Bale", "Micheal Cane"), LocalDate.parse("2005-06-15"));

                webTestClient
                .post()
                .uri("/v1/movieinfos")
                .bodyValue(movieInfo)
                        .exchange()
                        .expectStatus()
                        .isCreated()
                        .expectBody(MovieInfo.class)
                        .consumeWith(movieInfoEntityExchangeResult -> {
                            var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                            assert savedMovieInfo != null;
                            assert savedMovieInfo.getMovieInfoId()!= null;

                        });


    }

    @Test
    void getAllMovieInfos(){
        webTestClient
                .get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getAllMovieInfoById(){
        var movieInfoId = "abc";
        webTestClient
                .get()
                .uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var movieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assertNotNull(movieInfo);
//                });

                .expectBody()
                .jsonPath("$.name").isEqualTo("The Dark Knight Rises");

    }

    @Test
    void updateMovieInfo() {

        var movieInfo = new MovieInfo(null, "Batman Beguns", 2005, List.of("Christian Bale", "Micheal Cane"), LocalDate.parse("2005-06-15"));
        var movieInfoId = "abc";
        webTestClient
                .put()
                .uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert updatedMovieInfo != null;
                    assert updatedMovieInfo.getMovieInfoId()!= null;
                    assertEquals("Batman Beguns", updatedMovieInfo.getName());

                });


    }

    @Test
    void deleteMovieInfo(){

        var movieInfoId = "abc";
        webTestClient
                .delete()
                .uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
                .exchange()
                .expectStatus()
                .isNoContent();



    }

    @Test
    void updateMovieInfo_notfound() {

        var movieInfo = new MovieInfo(null, "Batman Beguns", 2005, List.of("Christian Bale", "Micheal Cane"), LocalDate.parse("2005-06-15"));
        var movieInfoId = "def";
        webTestClient
                .put()
                .uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isNotFound()
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var updatedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assert updatedMovieInfo != null;
//                    assert updatedMovieInfo.getMovieInfoId()!= null;
//                    assertEquals("Batman Beguns", updatedMovieInfo.getName());
//
//                })
        ;
    }

    @Test
    void getAllMovieInfoByYear(){
        var Uri = UriComponentsBuilder.fromUriString(MOVIES_INFO_URL)
                .queryParam("year",2005)
                        .buildAndExpand().toUri();
        webTestClient
                .get()
                .uri(Uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(1);
    }

}