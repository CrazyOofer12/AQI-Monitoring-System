package com.aqi.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aqi.backend.model.User;

@RestController
@RequestMapping("/journal")
public class UserController {
    private Map<Long,User> usermap = new HashMap<>();

    @GetMapping
    private List<User> getAll(){
        return new ArrayList<>(usermap.values());
    }
    @PostMapping
    public boolean CreateEntry(@RequestBody User user){
        usermap.put(user.getId(),user);
        return true;
    }

    @GetMapping("id/{myid}")
    public User getContent(@PathVariable Long myid){
        return usermap.get(myid);
    }

    @DeleteMapping("id/{myid}")
    public User deleteMapping(@PathVariable Long myid){
        return usermap.remove(myid);
    }
    @PutMapping("id/{myid}/{}")
    public boolean putMapping(@PathVariable Long myid,@RequestBody User user){
        usermap.replace(myid,user);
    }

}
