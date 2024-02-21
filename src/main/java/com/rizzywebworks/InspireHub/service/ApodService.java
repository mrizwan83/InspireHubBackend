package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.ApodEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ApodService {
    void fetchAndSaveApodData();

    List<ApodEntity> getAllApodEntries();

    Optional<ApodEntity> getApodById(Long id);

    Optional<ApodEntity> getApodByDate(LocalDate date);
}
