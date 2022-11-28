package com.example.springsecurity.service.azureservicetoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerateServiceToken {

  private static final Logger LOG = LoggerFactory.getLogger(GenerateServiceToken.class);
  @Value("${azure.activedirectory.client-id}")
  private String clientId;
  @Value("${azure.activedirectory.client-secret}")
  private String clientSecret;
  @Value("${azure.activedirectory.tenant-id}")
  private String tenetId;

  @Autowired
  private RestTemplate baseRestTemplate;

  public GenerateServiceToken(RestTemplate baseRestTemplate) {
    this.baseRestTemplate = baseRestTemplate;

  }

  public String retrieveServiceToken(String resource) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "client_credentials");
    map.add("client_id", clientId);
    map.add("client_secret", clientSecret);
    map.add("scope", resource+"/.default");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    String microsoftUrl = "https://login.microsoftonline.com/" + tenetId + "/oauth2/v2.0/token";

    try {
      OAuthResponse response = baseRestTemplate.postForEntity(microsoftUrl, request, OAuthResponse.class).getBody();
      if (response != null) {
        return response.getAccess_token();
      }
    } catch (final HttpStatusCodeException httpStatusCodeException) {
      LOG.warn("Status Code: {} returned for url: {} ", httpStatusCodeException.getRawStatusCode(), microsoftUrl);
    }

    return null;
  }
}
