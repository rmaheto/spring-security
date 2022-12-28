package com.example.springsecurity.shared.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    @JsonProperty(value = "id")
    private String employeeId;

    @JsonProperty(value = "jobTitle")
    private String jobTitle;

    @JsonProperty(value = "mail")
    private String emailAddress;

    private String phoneNumber;

    private String peopleSoftLocationId;

    @JsonProperty(value = "givenName")
    private String firstName;

    @JsonProperty(value = "surname")
    private String lastName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty(value = "mobilePhone")
    public void setPhoneNumber(List<String> mobilePhone) {
        this.phoneNumber = mobilePhone.get(0);
    }

    public String getPeopleSoftLocationId() {
        return peopleSoftLocationId;
    }

    public void setPeopleSoftLocationId(String peopleSoftLocationId) {
        this.peopleSoftLocationId = peopleSoftLocationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "employeeId='" + employeeId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
