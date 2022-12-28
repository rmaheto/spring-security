package com.example.springsecurity.shared.user;

import java.security.Principal;


public class UserPrincipal implements Principal {

  private String employeeId;
  private String jobTitle;
  private String emailAddress;
  private String phoneNumber;
  private String firstName;
  private String lastName;
  private String peopleSoftLocationId;

  public UserPrincipal(UserDTO userDTO) {
    this.employeeId = userDTO.getEmployeeId();
    this.jobTitle = userDTO.getJobTitle();
    this.emailAddress = userDTO.getEmailAddress();
    this.phoneNumber = userDTO.getPhoneNumber();
    this.firstName= userDTO.getFirstName();
    this.lastName= userDTO.getLastName();
    this.peopleSoftLocationId = userDTO.getPeopleSoftLocationId();
  }

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

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserPrincipal)) {
      return false;
    }

    UserPrincipal that = (UserPrincipal) o;

    if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) {
      return false;
    }
    if (jobTitle != null ? !jobTitle.equals(that.jobTitle) : that.jobTitle != null) {
      return false;
    }
    if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) {
      return false;
    }
    if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) {
      return false;
    }
    return peopleSoftLocationId != null ? peopleSoftLocationId.equals(that.peopleSoftLocationId) : that.peopleSoftLocationId == null;
  }

  @Override
  public int hashCode() {
    int result = employeeId != null ? employeeId.hashCode() : 0;
    result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
    result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
    result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
    result = 31 * result + (peopleSoftLocationId != null ? peopleSoftLocationId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserPrincipal{" +
            "employeeId='" + employeeId + '\'' +
            ", jobTitle='" + jobTitle + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", peopleSoftLocationId='" + peopleSoftLocationId + '\'' +
            '}';
  }

  @Override
  public String getName() {
    return null;
  }
}
