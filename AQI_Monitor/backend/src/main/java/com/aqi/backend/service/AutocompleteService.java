package com.aqi.backend.service;

import com.aqi.backend.model.City;
import com.aqi.backend.repository.AutocompleteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutocompleteService {
    @Autowired
    private AutocompleteRepo autocompleteRepo;

    @Cacheable(value="autocompleteCache",key="#query")
    public List<City> autoComplete(String query){
        return autocompleteRepo.findTop5ByCityStartingWithIgnoreCase(query);
    }
}
