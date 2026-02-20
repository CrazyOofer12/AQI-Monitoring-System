package com.aqi.backend.service;

import com.aqi.backend.model.GovernmentApiResponse;
import com.aqi.backend.model.GovtRecord;
import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AQIRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class AqiApiService {
    private final WebClient webClient;
    private final AQIRepo repository;
    private final ObjectMapper objectMapper;


    @Autowired
    public AqiApiService(WebClient webClient, AQIRepo repository, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }
    @Value("${govt.api.url}")
    private String apiUrl;

    @Value("${govt.api.key}")
    private String apiKey;

    public @Nullable GovernmentApiResponse fetchAqiData() {

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .queryParam("api-key", apiKey)
                        .queryParam("format", "json")
                        .queryParam("limit", "all") // safer
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);

        try {
            return objectMapper.readValue(response, GovernmentApiResponse.class);
        } catch (Exception e) {
            System.out.println("RAW RESPONSE:");
            System.out.println(response);
            throw new RuntimeException("API did not return valid JSON", e);
        }
    }

    public void storeAqiData(GovernmentApiResponse response) {

        Map<String, LocationData> locationMap = new HashMap<>();

        for (GovtRecord node : response.getRecords()) {

            String station = node.getStation();
            String city = node.getCity();
            String key = station + "|" + city;

            LocationData record = locationMap.getOrDefault(key, new LocationData());

            if (!locationMap.containsKey(key)) {
                record.setCountry(node.getCountry());
                record.setState(node.getState());
                record.setCity(city);
                record.setStation(station);
                record.setLatitude(node.getLatitude());
                record.setLongitude(node.getLongitude());
                record.setLastUpdate(node.getLastUpdate());
                record.setPollutants(new ArrayList<>());
            }

            LocationData.Pollutant pollutant = new LocationData.Pollutant();
            pollutant.setPollutantId(node.getPollutantId());
            pollutant.setMinValue(node.getMinValue());
            pollutant.setMaxValue(node.getMaxValue());
            pollutant.setAvgValue(node.getAvgValue());

            record.getPollutants().add(pollutant);

            locationMap.put(key, record);
        }

        for (LocationData record : locationMap.values()) {
            repository.findByStationAndCity(record.getStation(), record.getCity())
                    .ifPresentOrElse(existing -> {
                        existing.setPollutants(record.getPollutants());
                        existing.setLastUpdate(record.getLastUpdate());
                        repository.save(existing);
                    }, () -> repository.save(record));
        }

        System.out.println("AQI data saved/updated: " + locationMap.size() + " locations.");
    }

    public void fetchandsave(){
        storeAqiData(fetchAqiData());
    }

}