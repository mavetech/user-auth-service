package com.istio.demo.userauthservice.controller;

import com.istio.demo.userauthservice.Model.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class UserAuthenticationController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user-data-service.url}")
    private String userDataServiceUrl;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        log.info("Inside /login controller");
        String userDataResponse = fetchUserDataFromSecondMicroservice(credentials.getUsername());
        return ResponseEntity.ok(userDataResponse);
    }

    private String fetchUserDataFromSecondMicroservice(String username) {
        String secondMicroserviceBaseUrl = "http://" + userDataServiceUrl+ "/user_data/" + username;
        log.info("URI: "+secondMicroserviceBaseUrl);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                secondMicroserviceBaseUrl,
                HttpMethod.GET,
                null,
                String.class
        );
        log.info("Response: " + responseEntity.toString());

        String responseBody = null;//responseEntity.getBody();
        if (responseBody != null) {
            return responseBody;
        } else {
            // Handle the case when the response body is null (empty response)
            return "Empty response from user-data-service";
        }
    }
}
