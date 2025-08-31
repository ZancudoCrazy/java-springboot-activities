package com.adrian.practice.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.practice.app.components.HandlerError.HttpException;

@RestController
public class IndexController {
    
    @GetMapping("/not-found")
    public ResponseEntity<Map<String, String>> index(){
        throw HttpException.notFound("Resource not found");
    }
}
