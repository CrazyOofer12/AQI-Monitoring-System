package com.aqi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories("com.aqi.backend.repository")
public class AqiApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.aqi.backend.AqiApplication.class, args);
    }

}