package com.aqi.backend.repository;

import com.aqi.backend.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;



public interface TokenRepo
        extends MongoRepository<VerificationToken, String> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByEmail(String email);

    void deleteByEmail(String email);
}