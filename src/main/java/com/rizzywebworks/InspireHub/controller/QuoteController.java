package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.entity.QuoteEntity;
import com.rizzywebworks.InspireHub.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    // Endpoint to fetch and save a new quote
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveQuote() {
        try {
            quoteService.fetchAndSaveQuote();
            return ResponseEntity.ok("Quote fetched and saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch and save quote: " + e.getMessage());
        }
    }

    // Endpoint to get all quotes
    @GetMapping("/")
    public ResponseEntity<List<QuoteEntity>> getAllQuotes() {
        List<QuoteEntity> quotes = quoteService.getAllQuotes();
        return ResponseEntity.ok(quotes);
    }

    // Endpoint to get a quote by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuoteEntity> getQuoteById(@PathVariable Long id) {
        QuoteEntity quote = quoteService.getQuoteById(id);
        if (quote != null) {
            return ResponseEntity.ok(quote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get a random quote
    @GetMapping("/random")
    public ResponseEntity<QuoteEntity> getRandomQuote() {
        QuoteEntity quote = quoteService.getRandomQuote();
        return ResponseEntity.ok(quote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuote(@PathVariable Long id) {
        try {
            quoteService.deleteById(id);
            return ResponseEntity.ok("Quote with ID " + id + " deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete quote with ID " + id + ": " + e.getMessage());
        }
    }

}
