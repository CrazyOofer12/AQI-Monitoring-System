package com.aqi.backend.controller;

import com.aqi.backend.model.City;
import com.aqi.backend.repository.AutocompleteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutocompleteController {

    @Autowired
    AutocompleteRepo autocompleteRepo;

    @GetMapping("/autocomplete")
    public List<City> autoComplete(@RequestParam String query){
        return autocompleteRepo.findByCityStartingWithIgnoreCase(query);
    }
}
