package com.rizzywebworks.InspireHub.utils;

import com.rizzywebworks.InspireHub.service.ApodService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchApodDataTask {

    private final ApodService apodService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchApodDataDaily() {
        apodService.fetchAndSaveApodData();
    }

}
