package com.aqi.backend.repository;

import com.aqi.backend.model.LocationData;
import com.aqi.backend.model.MapDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapRepository{
    private final AQIRepo aqiRepo;

    public MapRepository(AQIRepo aqiRepo) {
        this.aqiRepo = aqiRepo;
    }

    public List<LocationData> findAllLocations() {
        return aqiRepo.findAll();
    }
}
