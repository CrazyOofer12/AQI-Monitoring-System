package com.aqi.backend.controller;

import com.aqi.backend.model.AqiCalculator;
import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AutocompleteRepo;
import com.aqi.backend.service.AqiService;
import com.aqi.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AQIController {

    @Autowired
    private AutocompleteRepo autocompleteRepo;
    @Autowired
    private SearchService service;


    @GetMapping("/Aqi")
    public ResponseEntity<?> giveAQI(@RequestParam String lat,@RequestParam String lon){
        GeoJsonPoint g = new GeoJsonPoint(Double.parseDouble(lon),Double.parseDouble(lat));
        LocationData l = service.nearestCoordinates(g);
        try {
            return new ResponseEntity<>(l, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
