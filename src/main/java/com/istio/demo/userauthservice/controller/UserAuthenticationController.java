package com.istio.demo.userauthservice.controller;

import com.istio.demo.userauthservice.Model.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@RestController
public class UserAuthenticationController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user-data-service.uri}")
    private String uri;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        log.info("Inside /login controller");
        String userDataResponse = String.valueOf(fetchUserDataFromSecondMicroservice(credentials.getUsername()));
        if (userDataResponse == null || userDataResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Empty response from user-data-service");
        }

        return ResponseEntity.ok(userDataResponse);
    }

    private ResponseEntity<String> fetchUserDataFromSecondMicroservice(String username) {
        String secondMicroserviceBaseUrl = "http://" + uri+ "/user_data/" + username;
        log.info("URI: " + secondMicroserviceBaseUrl);
        log.info("This is new!!!");

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    secondMicroserviceBaseUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            log.info("Response: " + responseEntity);

            log.info("Status: " + responseEntity.getStatusCode());

            // Check if the HTTP status code is 404 (Not Found)
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle the 404 case here (e.g., return a specific message)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty response from user-data-service");
            }

            String responseBody = responseEntity.getBody();

            return ResponseEntity.ok(responseBody);

        } catch (HttpClientErrorException ex) {
            // Handle the HttpClientErrorException
            // For example, you can log the error or return a specific error message
            log.error("HttpClientErrorException: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Unable to fetch user data from user-data-service");
        }
    }
}
