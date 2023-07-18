package com.istio.demo.userauthservice.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCredentials {
    private String username;
    private String password;

    // Getters and setters (omitted for brevity).
}

