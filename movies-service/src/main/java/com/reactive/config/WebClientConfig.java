package com.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.build();
    }
    /*
    So there's a class called WebClientAutoConfiguration which takes care of auto configuring the
    Webclient for you, it returns a WebClient.Builder, we can use this Builder in order to create
    Webclients. We can use this builder to automatically create an webclient instance for our use
     */
}
