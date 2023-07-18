package com.istio.demo.userauthservice.config;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class UserAuthRestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8082")); // Replace "localhost" with the actual hostname or IP address of user-data-service.
    }
}
