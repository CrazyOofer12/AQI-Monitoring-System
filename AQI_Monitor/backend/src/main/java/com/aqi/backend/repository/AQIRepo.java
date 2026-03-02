package com.aqi.backend.repository;

import com.aqi.backend.model.LocationData;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AQIRepo extends MongoRepository<LocationData,String>{

    Optional<LocationData> findByStationAndCity(String station, String city);

    LocationData findFirstByLocationNear(
            GeoJsonPoint point,
            Distance distance
    );
}
