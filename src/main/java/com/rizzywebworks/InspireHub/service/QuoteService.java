package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.QuoteEntity;

import java.util.List;

public interface QuoteService {

    // Method to fetch and save a new quote
    void fetchAndSaveQuote();

    // Method to retrieve all quotes
    List<QuoteEntity> getAllQuotes();

    // Method to retrieve a quote by its ID
    QuoteEntity getQuoteById(Long id);

    // Method to retrieve a random quote
    QuoteEntity getRandomQuote();
}
