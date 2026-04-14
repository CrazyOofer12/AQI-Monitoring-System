package com.aqi.backend.service;

import com.aqi.backend.model.MapDto;
import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AQIRepo;
import com.aqi.backend.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MapService {

    private final MapRepository MapRepository;

    private final AQIRepo aqiRepo;

    private boolean dataReady = false;

    private List<MapDto> cachedMapData = new ArrayList<>();

    public MapService(MapRepository MapRepository, AQIRepo aqiRepo) {
        this.MapRepository = MapRepository;
        this.aqiRepo = aqiRepo;
    }

    public void init() {
        List<LocationData> rawData = aqiRepo.findAll();

        cachedMapData = rawData.stream()
                .map(this::convertToDTO)
                .toList();

        System.out.println("Map data loaded: " + cachedMapData.size());
    }

    private MapDto convertToDTO(LocationData d) {

        double lat = 0;
        double lon = 0;

        if (d.getLocation() != null && d.getLocation().getCoordinates() != null) {

            List<Double> coords = d.getLocation().getCoordinates();

            lat = coords.get(1);
            lon = coords.get(0);

            // If reversed:
            // lat = coords.get(1);
            // lon = coords.get(0);
        }

        int aqi = d.getAqi();

        return new MapDto(
                d.getCity(),
                d.getStation(),
                lat,
                lon,
                aqi
        );
    }

    public List<MapDto> getMapData() {

        if (!dataReady) {
            System.out.println("Data not ready yet...");
            return List.of(); // or throw exception
        }

        return cachedMapData;
    }
    public void refreshMapCache() {

        List<LocationData> rawData = MapRepository.findAllLocations();

        cachedMapData = rawData.stream()
                .map(this::convertToDTO)
                .toList();

        dataReady = true;

        System.out.println("Map cache refreshed: " + cachedMapData.size());
    }
}