package com.istio.demo.userauthservice.controller;

import com.istio.demo.userauthservice.Model.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
    private String url;

    private String userDataServiceUrl;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        log.info("Inside /login controller");
        String userDataResponse = fetchUserDataFromSecondMicroservice(credentials.getUsername());
        return ResponseEntity.ok(userDataResponse);
    }

    private String fetchUserDataFromSecondMicroservice(String username) {
        String secondMicroserviceBaseUrl = "http://" + url+ "/user_data/" + username;
        log.info("URI: "+secondMicroserviceBaseUrl);
        log.info("This is new!!!");
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    secondMicroserviceBaseUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            log.info("Response: " + responseEntity);

            String responseBody = responseEntity.getBody();
            // Check if the response body is null (empty response)
            return Objects.requireNonNullElse(responseBody, "Empty response from user-data-service");

        } catch (HttpClientErrorException ex) {
            // Handle the HttpClientErrorException
            // For example, you can log the error or return a specific error message
            log.error("HttpClientErrorException: " + ex.getMessage());
            return "Error: Unable to fetch user data from user-data-service";
        }
    }
}
