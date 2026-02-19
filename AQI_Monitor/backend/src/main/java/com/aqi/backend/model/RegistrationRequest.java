package com.aqi.backend.model;

import java.time.Instant;

public class RegistrationRequest {

    private String regUsername;
    private String regEmail;
    private String regPassword;
    private Instant regCreatedAt;

    public String getRegUsername() {
        return regUsername;
    }

    public void setRegUsername(String regUsername) {
        this.regUsername = regUsername;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }

    public Instant getRegCreatedAt() {
        return regCreatedAt;
    }

    public void setRegCreatedAt(Instant regCreatedAt) {
        this.regCreatedAt = regCreatedAt;
    }
}
