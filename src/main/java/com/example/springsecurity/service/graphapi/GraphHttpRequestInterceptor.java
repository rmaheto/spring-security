package com.example.springsecurity.service.graphapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.example.springsecurity.utils.Constants.CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN;

public class GraphHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String graphApiToken = (String) httpServletRequest.getAttribute(CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN);
    request.getHeaders().set(HttpHeaders.AUTHORIZATION, graphApiToken);
    return execution.execute(request, body);
  }
}
