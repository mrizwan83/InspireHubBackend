package com.rizzywebworks.InspireHub.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rizzywebworks.InspireHub.entity.QuoteEntity;
import com.rizzywebworks.InspireHub.model.QuoteResponse;
import com.rizzywebworks.InspireHub.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService{

    private final RestTemplate restTemplate;
    private final QuoteRepository quoteRepository;

    // Implement the methods defined in the QuoteService interface



    // Retrieve API key from environment variable
    private final String QUOTE_API_KEY = System.getenv("QUOTE_API_KEY");
    private final String QUOTE_API_URL = "https://quotes.rest/qod?api_key=" + QUOTE_API_KEY;




    @Override
    public void fetchAndSaveQuote() {

        LocalDate today = LocalDate.now();
        String formattedDate = today.toString();
        if (quoteRepository.existsByDate(formattedDate)) {
            System.out.println("A quote has already been saved for today.");
            throw new RuntimeException("A quote has already been saved for today.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-TheySaidSo-Api-Secret", QUOTE_API_KEY);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(QUOTE_API_URL, HttpMethod.GET, null, String.class);
        System.out.println(responseEntity); //
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                QuoteResponse quoteResponse = objectMapper.readValue(responseBody, QuoteResponse.class);
                System.out.println(quoteResponse); //
                saveQuoteFromApiResponse(quoteResponse);
            } catch (Exception e) {
                System.err.println("Failed to parse API response: " + e.getMessage());
            }
        } else {
            System.err.println("Failed to fetch quote of the day. Status code: " + responseEntity.getStatusCodeValue());
        }
    }




    private void saveQuoteFromApiResponse(QuoteResponse quoteResponse) {
        if (quoteResponse != null && quoteResponse.getContents() != null && !quoteResponse.getContents().getQuotes().isEmpty()) {
            QuoteResponse.QuoteData quoteData = quoteResponse.getContents().getQuotes().getFirst();

            QuoteEntity quoteEntity = new QuoteEntity();
            quoteEntity.setQuote(quoteData.getQuote());
            quoteEntity.setAuthor(quoteData.getAuthor());
            quoteEntity.setCategory(quoteData.getCategory());
            quoteEntity.setPermalink(quoteData.getPermalink());
            quoteEntity.setDate(quoteData.getDate());

            // Save the mapped entity to the database
            quoteRepository.save(quoteEntity);
        } else {
            System.err.println("No quotes found in the API response.");
        }
    }
    @Override
    public List<QuoteEntity> getAllQuotes() {

        System.out.println("QUOTE_API_KEY: " + QUOTE_API_KEY);

        return quoteRepository.findAll();
    }

    @Override
    public QuoteEntity getQuoteById(Long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found with id: " + id));
    }

    @Override
    public QuoteEntity getRandomQuote() {
        return quoteRepository.findRandomQuote()
                .orElseThrow(() -> new RuntimeException("No quotes found in the database."));
    }

    @Override
    public void deleteById(Long id) {
        quoteRepository.deleteById(id);
    }
}
