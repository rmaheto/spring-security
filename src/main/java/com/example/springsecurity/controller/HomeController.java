package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "Welcome to your Dashboard";
    }

    @CrossOrigin
    @GetMapping("/home")
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
