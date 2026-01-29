package com.aqi.backend.controller;

import com.aqi.backend.model.User;
import com.aqi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private List<User> getAll(){
        return userService.find();
    }
    @PostMapping
    public boolean CreateEntry(@RequestBody User user){
        userService.SaveEntry(user);
        return true;
    }

    @GetMapping("id/{myid}")
    public User getContent(@PathVariable Long myid){
        return null;
    }

    @DeleteMapping("id/{myid}")
    public User deleteMapping(@PathVariable Long myid){
        return null;
    }
    @PutMapping("id/{myid}/{}")
    public boolean putMapping(@PathVariable Long myid,@RequestBody User user){
        return true;
    }


}
