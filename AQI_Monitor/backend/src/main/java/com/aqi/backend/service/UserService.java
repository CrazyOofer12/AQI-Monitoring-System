package com.aqi.backend.service;

import com.aqi.backend.model.RegistrationRequest;
import com.aqi.backend.model.User;
import com.aqi.backend.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private final UserRepo userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepository = userRepo;
    }

    public boolean saveNewUser(User user) throws UsernameNotFoundException{
        try {
            user.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.getPassword())));
            user.setAdmin(false) ;
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void registerUser(RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(Objects.requireNonNull(request.getUsername()));
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(request.getPassword())));
        user.setEnabled(true);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(String.valueOf(id));
    }

    public void deleteById(String id) {
        userRepository.deleteById(String.valueOf(id));
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public Optional<User> findByID(String myid) {
        return userRepository.findById(myid);
    }
}