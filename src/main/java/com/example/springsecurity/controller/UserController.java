package com.example.springsecurity.controller;

import com.example.springsecurity.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @CrossOrigin
    @GetMapping("/current-user")
    public User getCurrentUser(@RequestHeader(value="Accept") String acceptHeader,
                               @RequestHeader(value="Authorization") String authorizationHeader) {

        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("Accept", acceptHeader);
        returnValue.put("Authorization", authorizationHeader);
//        returnValue.put("graphApi",graphApiToken);
        System.out.println(returnValue);
        return new User();
    }
}
