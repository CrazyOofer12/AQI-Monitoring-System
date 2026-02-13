package com.aqi.backend.model;

import com.mongodb.lang.NonNull;
public class pUserDetails {
    private String id;
    private String username;
    private String password;
    private String email;              // ✅ Custom field
    private boolean enabled;
    private boolean isAdmin;

    public pUserDetails(
            String id,
            String username,
            String password,
            String email,
            boolean enabled,
            boolean admin
    ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.isAdmin=admin;

    }


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // ===== Spring Security Methods =====

    public String getPassword() {
        return password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
