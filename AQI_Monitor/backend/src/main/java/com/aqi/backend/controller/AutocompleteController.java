package com.aqi.backend.controller;

import com.aqi.backend.service.AutocompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String query, @RequestHeader(value = "X-Requested-With",required = false) String Header){
        if(Header==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(autocompleteService.autoComplete(query),HttpStatus.OK);
        }
    }
}
