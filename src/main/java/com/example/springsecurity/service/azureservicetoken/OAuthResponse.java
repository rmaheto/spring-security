package com.example.springsecurity.service.azureservicetoken;

import java.io.Serializable;

public class OAuthResponse implements Serializable {

  private static final long serialVersionUID = 1L;
  private String token_type;
  private String access_token;
  private String resource;
  private String expires_in;
  private String ext_expires_in;
  private String expires_on;
  private String not_before;

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(String expires_in) {
    this.expires_in = expires_in;
  }

  public String getExt_expires_in() {
    return ext_expires_in;
  }

  public void setExt_expires_in(String ext_expires_in) {
    this.ext_expires_in = ext_expires_in;
  }

  public String getExpires_on() {
    return expires_on;
  }

  public void setExpires_on(String expires_on) {
    this.expires_on = expires_on;
  }

  public String getNot_before() {
    return not_before;
  }

  public void setNot_before(String not_before) {
    this.not_before = not_before;
  }
}
