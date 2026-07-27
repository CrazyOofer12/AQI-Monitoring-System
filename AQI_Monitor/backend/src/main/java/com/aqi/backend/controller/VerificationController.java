package com.aqi.backend.controller;

import com.aqi.backend.model.User;
import com.aqi.backend.model.VerificationToken;
import com.aqi.backend.repository.UserRepo;
import com.aqi.backend.service.EmailService;
import com.aqi.backend.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.Optional;

@RestController
public class VerificationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepository;

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token){

        Optional<VerificationToken> optional = tokenService.getToken(token);

        if(optional.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        VerificationToken vt = optional.get();

        if(tokenService.isTokenExpired(vt)){
            return ResponseEntity.badRequest().body("Token expired");
        }

        Optional<User> userOpt = userRepository.findByEmail(vt.getEmail());

        if(userOpt.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();
        user.setEnabled(true);

        userRepository.save(user);

        tokenService.deleteToken(user.getEmail());

        return ResponseEntity.ok("Account verified successfully");
    }


    @PostMapping("/auth/resend-verification")
    public ResponseEntity<?> resendVerification(Authentication auth){

        String email = auth.getName();

        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();

        if(user.isEnabled()){
            return ResponseEntity.badRequest().body("Already verified");
        }

        String token = tokenService.createVerificationToken(email);

        String link = "http://localhost:8080/auth/verify?token=" + token;

        try {

            String template = emailService.loadTemplate("templates/email/verification-email.html");

            String html = template.replace("{{verify_link}}", link);

            emailService.sendEmail(
                    email,
                    "Verify your account",
                    html
            );

            return ResponseEntity.ok("Verification email sent");

        } catch (Exception e){

            return ResponseEntity.internalServerError()
                    .body("Failed to send email");

        }
    }
}