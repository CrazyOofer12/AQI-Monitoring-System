package com.aqi.backend.repository;

import com.aqi.backend.model.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetadataRepo extends MongoRepository<Metadata, String> {

}