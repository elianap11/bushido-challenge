package com.bushido.challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a stored record of a currency exchange rate query (API response model)")
public class QuoteDTO {

    @Schema(description = "Unique identifier of the stored Quote record (UUID)",
            example = "c4d5e6f7-a8b9-1234-d5e6-f7a8b9c0d1e2",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Timestamp when the quote was originally queried and stored",
            example = "2024-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime queryTimestamp;

    @Schema(description = "Base currency code used for the query (3 letters)",
            example = "USD",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String baseCurrency;

    @Schema(description = "Target currency code requested in the query (3 letters)",
            example = "EUR",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String targetCurrency;

    @Schema(description = "The exchange rate obtained from the base to the target currency at the query time",
            example = "0.915300", // Ejemplo con decimales
            accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal exchangeRate;

    @Schema(description = "Indicates if this quote record is considered active (relevant for potential logical delete scenarios)",
            example = "true",
            accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;
}