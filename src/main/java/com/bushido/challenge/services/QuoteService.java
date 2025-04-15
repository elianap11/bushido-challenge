package com.bushido.challenge.services;

import com.bushido.challenge.dtos.QuoteDTO;

import java.util.List;
import java.util.UUID;

public interface QuoteService {
    QuoteDTO consultAndSaveQuote(String fromCurrency, String toCurrency);
    List<QuoteDTO> getAllSavedQuotes();
    QuoteDTO getQuoteById(UUID id);
}
