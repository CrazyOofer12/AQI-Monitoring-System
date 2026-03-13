package com.aqi.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableMongoRepositories("com.aqi.backend.repository")
@EnableScheduling
@EnableCaching
public class AqiApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("GOVT_API_KEY",dotenv.get("GOVT_API_KEY"));
        SpringApplication.run(com.aqi.backend.AqiApplication.class, args);
    }
}