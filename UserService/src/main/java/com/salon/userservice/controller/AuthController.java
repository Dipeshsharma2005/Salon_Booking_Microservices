package com.salon.userservice.controller;

import com.salon.userservice.payload.dto.LoginDTO;
import com.salon.userservice.payload.dto.RefreshTokenRequest;
import com.salon.userservice.payload.dto.SignupDTO;
import com.salon.userservice.payload.response.AuthResponse;
import com.salon.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupDTO signupDTO) throws Exception {

        AuthResponse response= authService.signup(signupDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) throws Exception {

        AuthResponse response= authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/access-token/refresh-token")
    public ResponseEntity<AuthResponse> getAccessToken(
            @RequestBody RefreshTokenRequest request
    ) throws Exception {

        AuthResponse response =
                authService.getAccessTokenFromRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(response);
    }

}
