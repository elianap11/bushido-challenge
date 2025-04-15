package com.bushido.challenge.services.external;

import com.bushido.challenge.dtos.FrankfurterResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FrankfurterService{

    private final WebClient frankfurterWebClient;

    public Mono<FrankfurterResponseDTO> getLatestRate(String fromCurrency, String toCurrency) {
        log.info("Requesting rate from Frankfurter API: {} -> {}", fromCurrency, toCurrency);
        return frankfurterWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("from", fromCurrency)
                        .queryParam("to", toCurrency)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            log.error("Error response from Frankfurter API: status={}, body={}",
                                    clientResponse.statusCode(),
                                    clientResponse.bodyToMono(String.class).defaultIfEmpty("<empty body>"));
                            return Mono.error(new RuntimeException("Error fetching rate from Frankfurter: " + clientResponse.statusCode()));
                        })
                .bodyToMono(FrankfurterResponseDTO.class) // Convierte el cuerpo a nuestro DTO
                .doOnError(error -> log.error("Error during Frankfurter API call for {} -> {}", fromCurrency, toCurrency, error))
                .doOnSuccess(response -> log.info("Successfully retrieved rate from Frankfurter: {}", response));
    }

    public Optional<BigDecimal> extractRate(FrankfurterResponseDTO response, String targetCurrency) {
        if (response == null || response.getRates() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(response.getRates().get(targetCurrency));
    }
}