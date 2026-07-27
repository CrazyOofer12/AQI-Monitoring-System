package com.aqi.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import org.springframework.web.reactive.function.BodyInserters;

@Service
public class EmailService {

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.from}")
    private String from;

    private final WebClient webClient;

    public EmailService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.mailgun.net/")
                .build();
    }



//    public void sendEmail(String to, String subject, String html) {
//
//        webClient.post()
//                .uri("/{domain}/messages", domain)
//                .headers(headers -> headers.setBasicAuth("api", apiKey))
//                .body(
//                        BodyInserters
//                                .fromFormData("from", from)
//                                .with("to", to)
//                                .with("subject", subject)
//                                .with("html", html)
//                )
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
public void sendEmail(String to, String subject, String html) {

    String response = webClient.post()
            .uri("/{domain}/messages", domain)
            .headers(headers -> headers.setBasicAuth("api", apiKey))
            .body(
                    org.springframework.web.reactive.function.BodyInserters
                            .fromFormData("from", from)
                            .with("to", to)
                            .with("subject", subject)
                            .with("html", html)
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();

    System.out.println("Mailgun response: " + response);
}
    public String loadTemplate(String path) throws Exception {

        ClassPathResource resource = new ClassPathResource(path);

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
