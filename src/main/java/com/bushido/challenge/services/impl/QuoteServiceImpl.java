package com.bushido.challenge.services.impl;

import com.bushido.challenge.dtos.FrankfurterResponseDTO;
import com.bushido.challenge.dtos.QuoteDTO;
import com.bushido.challenge.entities.Quote;
import com.bushido.challenge.exceptions.ResourceNotFoundException;
import com.bushido.challenge.mapper.QuoteMapper;
import com.bushido.challenge.repositories.QuoteRepository;
import com.bushido.challenge.services.QuoteService;
import com.bushido.challenge.services.external.FrankfurterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final FrankfurterService frankfurterService;

    @Override
    public QuoteDTO consultAndSaveQuote(String fromCurrency, String toCurrency) {
        log.info("Processing quote request: {} -> {}", fromCurrency, toCurrency);

         FrankfurterResponseDTO response = frankfurterService.getLatestRate(fromCurrency, toCurrency)
                .block();

        if (response == null) {
            log.error("No response received from Frankfurter API for {} -> {}", fromCurrency, toCurrency);
            throw new RuntimeException("Failed to get response from currency API.");
        }

         BigDecimal rate = frankfurterService.extractRate(response, toCurrency)
                .orElseThrow(() -> {
                    log.error("Target currency '{}' not found in Frankfurter response rates: {}", toCurrency, response.getRates());
                    return new ResourceNotFoundException("Exchange rate not available for target currency: " + toCurrency);
                });

        Quote quote = new Quote();
        quote.setQueryTimestamp(LocalDateTime.now());
        quote.setBaseCurrency(fromCurrency.toUpperCase());
        quote.setTargetCurrency(toCurrency.toUpperCase());
        quote.setExchangeRate(rate);
        quote.setActive(true);

        Quote savedQuote = quoteRepository.save(quote);
        log.info("Saved new quote with ID: {}", savedQuote.getId());

         return QuoteMapper.toQuoteDTO(savedQuote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDTO> getAllSavedQuotes() {
        List<Quote> quotes = quoteRepository.findAll();
        return QuoteMapper.toQuoteDTOs(quotes);
    }

    @Override
    @Transactional(readOnly = true)
    public QuoteDTO getQuoteById(UUID id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id: " + id));
        return QuoteMapper.toQuoteDTO(quote);
    }
}
