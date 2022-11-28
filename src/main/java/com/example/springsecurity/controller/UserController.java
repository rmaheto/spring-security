package com.example.springsecurity.controller;

import com.example.springsecurity.service.RetrieveUserService;
import com.example.springsecurity.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private RetrieveUserService retrieveUserService;

    @CrossOrigin
    @GetMapping("/current-user")
    public User getCurrentUser() {
        return retrieveUserService.retrieveCurrentUser();
    }
}
