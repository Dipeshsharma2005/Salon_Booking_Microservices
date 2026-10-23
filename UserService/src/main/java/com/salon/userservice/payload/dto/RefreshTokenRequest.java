package com.salon.userservice.payload.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}