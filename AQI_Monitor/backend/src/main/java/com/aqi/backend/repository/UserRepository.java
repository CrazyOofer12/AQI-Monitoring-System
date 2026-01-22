package com.aqi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;

@Repository
public class UserRepository extends JpaRepository<> {
    public <T> ScopedValue<T> findById(Long id) {
        return null;
    }
}
