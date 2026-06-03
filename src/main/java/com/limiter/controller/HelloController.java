package com.limiter.controller;

import com.limiter.custom_annotations.RateLimited;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @RateLimited(seconds = 1, requests = 5)
    public ResponseEntity<String> getHello(HttpServletRequest httpServletRequest) {

        System.out.println("request served");
        return ResponseEntity.ok("Hello! How are you. This is my first CI/CD project.");
    }

    @GetMapping("/hello2")
    @RateLimited(seconds = 2, requests = 15)
    public ResponseEntity<String> getHello2(HttpServletRequest httpServletRequest) {

        System.out.println("request served");
        return ResponseEntity.ok("Hello!");
    }
}
