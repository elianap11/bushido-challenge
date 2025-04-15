package com.bushido.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${frankfurter.api.base-url}")
    private String frankfurterBaseUrl;

    @Bean
    public WebClient frankfurterWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(frankfurterBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    };
}