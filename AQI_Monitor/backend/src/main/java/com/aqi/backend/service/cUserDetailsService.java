package com.aqi.backend.service;

import com.aqi.backend.model.User;
import com.aqi.backend.model.pUserDetails;
import com.aqi.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class cUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {


        System.out.println("LOGIN ATTEMPT WITH: " + email);  // ✅ DEBUG

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("USER NOT FOUND");
                    return new UsernameNotFoundException("User not found");
                });
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println("USER FOUND");
        System.out.println(STR."Stored password: \{user.getPassword()}");

        return new pUserDetails(user);
    }
}