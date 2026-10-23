package com.salon.userservice.service.implementation;

import com.salon.userservice.model.User;
import com.salon.userservice.payload.dto.SignupDTO;
import com.salon.userservice.payload.response.AuthResponse;
import com.salon.userservice.payload.response.TokenResponse;
import com.salon.userservice.repository.UserRepository;
import com.salon.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final KeyCloakService  keyCloakService;

    @Override
    public AuthResponse login(String username, String password) {
        try {
            TokenResponse tokenResponse = keyCloakService.getAdminAccessToken(username, password, "password", null);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setRefreshToken(tokenResponse.getRefreshToken());
            authResponse.setJwt(tokenResponse.getAccessToken());
            authResponse.setMessage("Login Successfully");
            return authResponse;

        } catch (Exception ex) {
            // propagate HTTP 401
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    @Override
    public AuthResponse signup(SignupDTO request) throws Exception {
        keyCloakService.createUser(request);
System.out.println("in signup service");
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setFullName(request.getFirstName()+" "+request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        TokenResponse tokenResponse=keyCloakService.getAdminAccessToken(request.getUsername(),request.getPassword(),"password",null);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Registered Successfully");
        authResponse.setRole(user.getRole());

        return authResponse;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {

        TokenResponse tokenResponse=keyCloakService.getAdminAccessToken(null,null,"refresh_token",refreshToken);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Token refreshed successfully");

        return authResponse;
    }
}
