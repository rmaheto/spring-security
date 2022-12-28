package com.example.springsecurity.config.rest;

import com.example.springsecurity.service.graphapi.GraphHttpRequestInterceptor;
import com.example.springsecurity.shared.restframework.interceptor.OutboundRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RemoteServicesConfig {

    @Autowired
    @Lazy
    private OutboundRequestInterceptor outboundRequestInterceptor;


    @Bean
    public RestTemplate baseRestTemplate(){
        RestTemplate base = new RestTemplate();
        base.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory()));
        return base;
    }

    @Bean
    public RestTemplate apiRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(outboundRequestInterceptor);
        return restTemplate;
    }

    @Bean
    public RestTemplate graphRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new GraphHttpRequestInterceptor());
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());

        // default to timing out connections
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(10000); // 10 seconds
        httpComponentsClientHttpRequestFactory.setConnectTimeout(10000); // 10 seconds
        httpComponentsClientHttpRequestFactory.setReadTimeout(40000); // 40 seconds
        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    @Primary
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder builder = HttpClientBuilder.create();

        // default the total connections
        builder.setMaxConnTotal(200);
        builder.setMaxConnPerRoute(100);
        builder.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build());
        return builder;
    }

    @Bean
    public HttpClient httpClient() {
        return httpClientBuilder().build();
    }
}
