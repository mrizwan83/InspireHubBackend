package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.entity.ApodEntity;
import com.rizzywebworks.InspireHub.service.ApodService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/apod")
@RequiredArgsConstructor
public class ApodController {

    private final ApodService apodService;


    @GetMapping("/fetch")
    public String fetchAndSaveApodData() {
        try {
            apodService.fetchAndSaveApodData();
            return "APOD data fetched and saved successfully!";
        } catch (Exception e) {
            return "Failed to fetch and save APOD data: " + e.getMessage();
        }
    }

    // Endpoint to get all APOD entries
    @GetMapping
    public ResponseEntity<List<ApodEntity>> getAllApodEntries() {
        List<ApodEntity> apodEntries = apodService.getAllApodEntries();
        return new ResponseEntity<>(apodEntries, HttpStatus.OK);
    }

    // Endpoint to get APOD by date
    @GetMapping("/{date}")
    public ResponseEntity<ApodEntity> getApodByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ApodEntity apodEntity = apodService.getApodByDate(date);
        if (apodEntity != null) {
            return new ResponseEntity<>(apodEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<ApodEntity> getApodById(@PathVariable Long id) {
        ApodEntity apodEntity = apodService.getApodById(id);
        if (apodEntity != null) {
            return new ResponseEntity<>(apodEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Add more endpoints for additional querying methods as needed
}
