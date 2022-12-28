package com.example.springsecurity.service.graphapi;

import com.example.springsecurity.shared.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GraphService {

  @Value("${azure.activedirectory.graphApiUrl}")
  private String graphApiURL;

  @Autowired
  private RestTemplate graphRestTemplate;

//  public GraphService(RestTemplate graphRestTemplate) {
//    this.graphRestTemplate = graphRestTemplate;
//  }

  public UserDTO retrieveUser(String graphAPIToken) {
    UserDTO userDTO = graphRestTemplate.getForEntity(graphApiURL, UserDTO.class).getBody();
    if (userDTO == null || userDTO.getEmployeeId() == null) {
      throw new RuntimeException("Unable to acquire EID from Graph API");
    }
    return userDTO;
  }
}
