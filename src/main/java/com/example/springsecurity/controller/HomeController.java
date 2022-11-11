package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "Welcome to your Dashboard";
    }

    @GetMapping("/")
    public String homepage(){
        return "Welcome to Spring Security Demo";
    }
    @GetMapping("/user")
    public String userProfile(){
        return "User Profile Page";
    }

    @GetMapping("/admin")
    public String adminProfile(){
        return "Admin Profile Page";
    }

    @DeleteMapping("/delete")
    public String deleteEndpoint(@RequestBody String s) {
        return "I am deleting " + s;
    }
}
