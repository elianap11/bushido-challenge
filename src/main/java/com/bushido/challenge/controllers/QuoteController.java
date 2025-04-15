package com.bushido.challenge.controllers;
import com.bushido.challenge.dtos.QuoteDTO;
import com.bushido.challenge.services.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Currency Quotes", description = "APIs for retrieving and storing currency exchange rates from an external source.")
public class QuoteController {

    private final QuoteService quoteService;

    @Operation(summary = "Get latest currency exchange rate",
            description = "Fetches the latest exchange rate between two currencies from the Frankfurter API, stores the result, and returns it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchange rate retrieved and stored successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuoteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid currency codes provided (must be 3 letters)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Exchange rate not available for the requested target currency",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Error communicating with the external Frankfurter API or other internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/latest")
    public ResponseEntity<QuoteDTO> getLatestQuote(
            @Parameter(description = "Base currency code (3 letters, e.g., USD)", required = true, example = "USD")
            @RequestParam @NotBlank @Size(min = 3, max = 3, message = "Base currency code must be 3 letters") String from,
            @Parameter(description = "Target currency code (3 letters, e.g., EUR)", required = true, example = "EUR")
            @RequestParam @NotBlank @Size(min = 3, max = 3, message = "Target currency code must be 3 letters") String to) {

        QuoteDTO quote = quoteService.consultAndSaveQuote(from.toUpperCase(), to.toUpperCase());
        return ResponseEntity.ok(quote);
    }

    @Operation(summary = "Get all saved quotes", description = "Retrieves a list of all previously stored currency quote lookups.")
    @ApiResponse(responseCode = "200", description = "List of saved quotes retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(type="array", implementation = QuoteDTO.class)))
    @GetMapping
    public ResponseEntity<List<QuoteDTO>> getAllSavedQuotes() {
        List<QuoteDTO> quotes = quoteService.getAllSavedQuotes();
        return ResponseEntity.ok(quotes);
    }

    @Operation(summary = "Get saved quote by ID", description = "Retrieves the details of a specific stored currency quote lookup.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saved quote found", content = @Content(schema = @Schema(implementation = QuoteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Saved quote not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuoteDTO> getSavedQuoteById(
            @Parameter(description = "UUID of the saved quote to retrieve", required = true) @PathVariable UUID id) {
        QuoteDTO quote = quoteService.getQuoteById(id);
        return ResponseEntity.ok(quote);
    }
}