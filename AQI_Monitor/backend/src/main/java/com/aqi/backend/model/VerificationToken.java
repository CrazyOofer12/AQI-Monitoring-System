package com.aqi.backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "verification_tokens")
public class VerificationToken {

    @Id
    private String id;

    private String token;

    private String email;

    private LocalDateTime expiryTime;

    public VerificationToken(){}

    public VerificationToken(String token, String email, LocalDateTime expiryTime) {
        this.token = token;
        this.email = email;
        this.expiryTime = expiryTime;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public LocalDateTime getExpiryTime() { return expiryTime; }

    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }
}