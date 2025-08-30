package com.adrian.practice.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> index(){
        return ResponseEntity.ok(Map.of("message", "Hello World"));
    }
}
