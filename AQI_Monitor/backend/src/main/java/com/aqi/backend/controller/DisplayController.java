package com.aqi.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisplayController {

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/login.html";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "forward:/register.html";
    }
}
