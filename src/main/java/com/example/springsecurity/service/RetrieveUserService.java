package com.example.springsecurity.service;

import com.example.springsecurity.shared.user.User;
import com.example.springsecurity.shared.user.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RetrieveUserService {

    public User retrieveCurrentUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getCurrentUser(principal);
    }


    User getCurrentUser(UserPrincipal currentUser) {
        if (currentUser != null) {
            User user = new User();
            user.setEmployeeId(currentUser.getEmployeeId());
            user.setFirstName(currentUser.getFirstName());
            user.setLastName(currentUser.getLastName());
            user.setJobTitle(currentUser.getJobTitle());
            user.setPhoneNumber(currentUser.getPhoneNumber());

            return user;
        }
        return null;
    }
}
