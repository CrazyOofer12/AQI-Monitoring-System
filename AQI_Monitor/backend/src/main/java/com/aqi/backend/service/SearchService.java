package com.aqi.backend.service;

import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AQIRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public LocationData nearestCoordinates(GeoJsonPoint geoJsonPoint){
        Distance maxdistance = new Distance(200, Metrics.KILOMETERS);

        return repository.findFirstByLocationNear(geoJsonPoint,maxdistance);
    }
//    public int findAQI(LocationData nearest){
//        List<?> l = new ArrayList<>(nearest.getPollutants());
//        for(l:)
//    }


}
