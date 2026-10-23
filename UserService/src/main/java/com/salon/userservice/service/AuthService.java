package com.salon.userservice.service;

import com.salon.userservice.payload.dto.SignupDTO;
import com.salon.userservice.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO request) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
