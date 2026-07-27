package com.aqi.backend.service;

import com.aqi.backend.model.Metadata;
import com.aqi.backend.repository.MetadataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AutomaticUpdater {

    private static final String METADATA_ID = "AQI_SNAPSHOT";

    @Autowired
    private AqiApiService govtApiService;

    @Autowired
    private MapService mapService;

    @Autowired
    private MetadataRepo metadataRepository;

    @Scheduled(fixedRate = 60 * 60 * 1000) // every hour
    public void updateAqiData() {

        Instant now = Instant.now();

        Metadata metadata = metadataRepository
                .findById(METADATA_ID)
                .orElse(null);

        if (metadata != null) {

            Duration diff = Duration.between(metadata.getLastUpdated(), now);

            if (diff.toMinutes() < 60) {
                System.out.println("Skipping AQI update (recently updated)");
                mapService.refreshMapCache();
                return;
            }
        }

        System.out.println("Fetching fresh AQI data...");

        govtApiService.fetchandsave();

        mapService.refreshMapCache();

        Metadata newMetadata = new Metadata();
        newMetadata.setId(METADATA_ID);
        newMetadata.setLastUpdated(now);

        metadataRepository.save(newMetadata);

        System.out.println("AQI update completed.");
    }
}