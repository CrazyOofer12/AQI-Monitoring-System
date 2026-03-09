package com.aqi.backend.controller;

import com.aqi.backend.model.LocationData;
import com.aqi.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/nearest")
    public ResponseEntity<?> getNearest(
            @RequestParam double lat,
            @RequestParam double lon
    ){
        return new ResponseEntity<>(searchService.nearestCoordinates(new GeoJsonPoint(lon,lat)), HttpStatus.OK);
    }
}
