package com.aqi.backend.controller;

import com.aqi.backend.model.MapDto;
import com.aqi.backend.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "*")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public List<MapDto> getMapData() {
        return mapService.getMapData();
    }
}