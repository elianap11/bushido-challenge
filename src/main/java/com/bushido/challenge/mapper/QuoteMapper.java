package com.bushido.challenge.mapper;

import com.bushido.challenge.dtos.QuoteDTO;
import com.bushido.challenge.entities.Quote;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class QuoteMapper {

     public static QuoteDTO toQuoteDTO(Quote quote) {
        if (quote == null) {
            return null;
        }
        return new QuoteDTO(
                quote.getId(),
                quote.getQueryTimestamp(),
                quote.getBaseCurrency(),
                quote.getTargetCurrency(),
                quote.getExchangeRate(),
                quote.isActive()
        );
    }

    public static List<QuoteDTO> toQuoteDTOs(List<Quote> quotes) {
        if (quotes == null) {
            return Collections.emptyList();
        }
        return quotes.stream()
                .map(QuoteMapper::toQuoteDTO)
                .collect(Collectors.toList());
    }

}
