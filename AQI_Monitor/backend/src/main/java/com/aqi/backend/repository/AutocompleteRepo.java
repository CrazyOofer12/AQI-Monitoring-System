package com.aqi.backend.repository;

import com.aqi.backend.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AutocompleteRepo extends MongoRepository <City,String> {
    List<City> findTop5ByCityStartingWithIgnoreCase(String prefix);

}
