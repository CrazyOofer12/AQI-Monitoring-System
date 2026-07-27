package com.aqi.backend.service;

import com.aqi.backend.model.VerificationToken;
import com.aqi.backend.repository.TokenRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepo tokenRepo;

    public TokenService(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public String createVerificationToken(String email) {

        tokenRepo.deleteByEmail(email);

        String token = UUID.randomUUID().toString();

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        VerificationToken verificationToken =
                new VerificationToken(token, email, expiry);

        tokenRepo.save(verificationToken);

        return token;
    }

    public Optional<VerificationToken> getToken(String token) {
        return tokenRepo.findByToken(token);
    }

    public void deleteToken(String email) {
        tokenRepo.deleteByEmail(email);
    }

    public boolean isTokenExpired(VerificationToken token) {
        return token.getExpiryTime().isBefore(LocalDateTime.now());
    }
}