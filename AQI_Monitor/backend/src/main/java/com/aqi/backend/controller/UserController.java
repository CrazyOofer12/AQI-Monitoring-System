package com.aqi.backend.controller;

import com.aqi.backend.model.RegistrationRequest;
import com.aqi.backend.model.User;
import com.aqi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity<?>  getAll(){
        List<User> l = userService.getAll();
        if (!l.isEmpty()){
            return new ResponseEntity<>(l,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> CreateEntry(@RequestBody RegistrationRequest registrationRequest){
        try {
            userService.registerUser(registrationRequest);
            return new ResponseEntity<>(registrationRequest,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getContent(@PathVariable String myid){
        Optional<User> u = userService.findByID(myid);
        return u.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deletebyid(@PathVariable String myid){
        userService.deleteById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myid}/{}")
    public ResponseEntity<?>  updateById(@PathVariable String myid, @RequestBody User newUser){
        User old = userService.findByID(myid).orElse(null);
        if(old!=null){
            old.setUsername(newUser.getUsername()!=null && !newUser.getUsername().isEmpty() ?newUser.getUsername(): old.getUsername());
            old.setUsername(newUser.getPassword()!=null && !newUser.getPassword().isEmpty() ?newUser.getPassword(): old.getPassword());
            userService.saveUser(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
