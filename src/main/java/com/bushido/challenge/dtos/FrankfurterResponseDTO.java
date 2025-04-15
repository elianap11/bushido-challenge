package com.bushido.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents the JSON response received from the Frankfurter API's /latest endpoint")
public class FrankfurterResponseDTO {

    @Schema(description = "The amount of the base currency for which the rates are provided (usually 1.0).", example = "1.0")
    private BigDecimal amount;

    @Schema(description = "The 3-letter ISO code of the base currency used in the request.", example = "USD")
    private String base;

    @Schema(description = "The date for which the exchange rates are provided.", example = "2024-01-15")
    @JsonProperty("date")
    private LocalDate date;

    @Schema(description = "A map containing the exchange rates from the base currency to the requested target currencies. " +
            "Keys are the 3-letter target currency codes, values are the exchange rates.",
            example = "{\"EUR\": 0.9153, \"JPY\": 145.88}")
    private Map<String, BigDecimal> rates;
}
