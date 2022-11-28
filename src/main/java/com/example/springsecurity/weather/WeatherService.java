package com.example.springsecurity.weather;

import com.example.springsecurity.config.restframework.template.BaseRestTemplate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("weatherService")
public class WeatherService {

    private final RestTemplate apiRestTemplate;

    @Value("${weather.apiKey}")
    private String weatherApiKey;

    @Value("${weather.api}")
    private String weatherUrl;

    public WeatherService(RestTemplate apiRestTemplate) {
        this.apiRestTemplate = apiRestTemplate;
    }

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<WeatherDto> getWeather() {

        ParameterizedTypeReference<List<WeatherDto>> typeRef = new ParameterizedTypeReference<List<WeatherDto>>(){};
        String url = weatherUrl;
        String url2 = "https://weatherapi-service.azurewebsites.net/";
        List<WeatherDto> response = apiRestTemplate.exchange(url, HttpMethod.GET,null, typeRef).getBody();
        return response;
    }

}
