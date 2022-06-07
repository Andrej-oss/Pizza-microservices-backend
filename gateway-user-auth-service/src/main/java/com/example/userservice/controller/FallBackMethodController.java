package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fallBack")
public class FallBackMethodController {

    @GetMapping("/userService")
    public String userServiceFallBackGetMethod() {
        return "User service is taking longer then expected." +
                "Please try again later";
    }
    @PostMapping("/userService")
    public String userServiceFallBackPostMethod() {
        return "User service is taking longer then expected." +
                "Please try again later";
    }
}
