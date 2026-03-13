package com.aqi.backend.service;

import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AQIRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

@Component
public class SearchService {

    private final AQIRepo repository;

    @Autowired
    SearchService(AQIRepo repository){
        this.repository = repository;
    }

    @Cacheable(value="aqiCache",key="#geoJsonPoint")
    public LocationData nearestCoordinates(GeoJsonPoint geoJsonPoint){
        Distance maxdistance = new Distance(200, Metrics.KILOMETERS);

        return repository.findFirstByLocationNear(geoJsonPoint,maxdistance);
    }



}
