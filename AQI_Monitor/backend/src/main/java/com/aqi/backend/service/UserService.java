package com.aqi.backend.service;

import com.aqi.backend.model.User;
import com.aqi.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void SaveEntry(User user){
        userRepo.save(user);
    }
    public List<User> find() {
        return userRepo.findAll();
    }


}