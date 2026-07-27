package com.aqi.backend.service;

import com.aqi.backend.model.City;
import com.aqi.backend.model.LocationData;
import com.aqi.backend.repository.AQIRepo;
import com.aqi.backend.repository.AutocompleteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AutocompleteService {
    @Autowired
    private AutocompleteRepo autocompleteRepo;

    @Autowired
    private AQIRepo aqiRepo;

    @Cacheable(value="autocompleteCache", key="#query")
    public List<City> autoComplete(String query){
        List<City> cities = autocompleteRepo.findTop5ByCityStartingWithIgnoreCase(query);
        if (cities == null || cities.isEmpty()) {
            syncCityCache();
            cities = autocompleteRepo.findTop5ByCityStartingWithIgnoreCase(query);
        }
        return cities;
    }

    private void syncCityCache() {
        try {
            List<LocationData> locations = aqiRepo.findAll();
            Set<String> seen = new HashSet<>();
            for (LocationData loc : locations) {
                if (loc.getCity() != null && !loc.getCity().isEmpty()) {
                    String key = loc.getCity() + "|" + loc.getState();
                    if (!seen.contains(key)) {
                        seen.add(key);
                        City c = new City();
                        c.setId(loc.getCity().replaceAll("\\s+", "_") + "_" + (loc.getState() != null ? loc.getState().replaceAll("\\s+", "_") : ""));
                        c.setCity(loc.getCity());
                        c.setState(loc.getState());
                        c.setLocation(loc.getLocation());
                        autocompleteRepo.save(c);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error syncing city cache: " + e.getMessage());
        }
    }
}
