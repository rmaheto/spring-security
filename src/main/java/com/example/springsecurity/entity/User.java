package com.example.springsecurity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 6123737363636L;

    private String FirstName;
    private String middleName;
    private String lastName;
    private String preferredName;
    private String group;
    private String branch;
    private String country;
    private String employeeId;
    private String jobTitle;
    private List<UserRoles> userRoles = new ArrayList<>();
    private List<SecuredObject> allSecuredObjects = new ArrayList<>();
}
