package com.salon.userservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salon.userservice.domain.UserRole;
import lombok.Data;

@Data
public class AuthResponse {

    @JsonProperty("access_token")
    private String jwt;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String message;
    private String title;
    private UserRole role;
}
