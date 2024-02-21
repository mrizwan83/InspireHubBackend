package com.rizzywebworks.InspireHub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rizzywebworks.InspireHub.entity.ApodEntity;
import com.rizzywebworks.InspireHub.repository.ApodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApodServiceImpl implements ApodService {

    private final String APOD_API_URL = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";

    private final ApodRepository apodRepository;
    private final RestTemplate restTemplate;

    @Override
    public void fetchAndSaveApodData() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(APOD_API_URL, String.class);
        String responseBody = responseEntity.getBody();

        // Parse the JSON response and map it to ApodEntity
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            LocalDate date = LocalDate.parse(jsonNode.get("date").asText());

            // Check if an entry for the same date already exists in the database
            if (!apodRepository.existsByDate(date)) {
                ApodEntity apodEntity = ApodEntity.builder()
                        .date(date)
                        .explanation(jsonNode.get("explanation").asText())
                        .hdUrl(jsonNode.get("hdurl").asText())
                        .mediaType(jsonNode.get("media_type").asText())
                        .serviceVersion(jsonNode.get("service_version").asText())
                        .title(jsonNode.get("title").asText())
                        .url(jsonNode.get("url").asText())
                        .build();

                // Save the fetched APOD data to the database
                apodRepository.save(apodEntity);
            } else {
                System.out.println("An entry for the date already exists in the database.");
            }
        } catch (JsonProcessingException e) {
            // Handle JSON parsing exception
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ApodEntity> getAllApodEntries() {
        return apodRepository.findAll();
    }

    @Override
    public Optional<ApodEntity> getApodByDate(LocalDate date) {
        return Optional.ofNullable(apodRepository.findByDate(date));
    }

    @Override
    public Optional<ApodEntity> getApodById(Long id) {
        return apodRepository.findById(id);
    }
}



