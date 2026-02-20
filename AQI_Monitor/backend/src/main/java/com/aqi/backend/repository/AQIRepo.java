package com.aqi.backend.repository;

import com.aqi.backend.model.LocationData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AQIRepo extends MongoRepository<LocationData,String>{

    Optional<LocationData> findByStationAndCity(String station, String city);
}
