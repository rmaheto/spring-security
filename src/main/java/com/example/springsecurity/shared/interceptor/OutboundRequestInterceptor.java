package com.example.springsecurity.shared.interceptor;

import com.example.springsecurity.service.azureservicetoken.GenerateServiceToken;
import com.example.springsecurity.shared.request.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.springsecurity.utils.Constants.BEARER;

@Component
public class OutboundRequestInterceptor implements ClientHttpRequestInterceptor {

    @Value("${weather.api}")
    private String weatherServiceUrl;
    @Value("${weather.api-resource}")
    private String weatherApiResource;
    @Autowired
    private GenerateServiceToken generateServiceToken;
    @Autowired
    private RequestContext requestContext;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        generateHeaders(request.getHeaders());
        try{
            if((new URI(weatherServiceUrl)).getHost().equals(request.getURI().getHost())){
                generateAuthHeader(request.getHeaders(),generateServiceToken.retrieveServiceToken(weatherApiResource));
            }
        }catch(URISyntaxException e){

        }
        return execution.execute(request,body);
    }

    private void generateHeaders(HttpHeaders headers){
        if(headers == null){
            headers = new HttpHeaders();
        }

        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    private void generateAuthHeader(HttpHeaders headers, String token){
        String bearerToken = BEARER +token;
        headers.set(HttpHeaders.AUTHORIZATION,bearerToken);
    }
}
