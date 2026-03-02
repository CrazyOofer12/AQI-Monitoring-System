package com.aqi.backend.controller;

import com.aqi.backend.model.LocationData;
import com.aqi.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/nearest")
    public LocationData getNearest(
            @RequestParam double lat,
            @RequestParam double lon
    ){
        return searchService.nearestCoordinates(lat,lon);
    }
}
