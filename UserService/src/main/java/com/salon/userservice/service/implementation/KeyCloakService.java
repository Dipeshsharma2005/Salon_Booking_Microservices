package com.salon.userservice.service.implementation;

import com.salon.userservice.payload.dto.*;
import com.salon.userservice.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyCloakService {
    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL + "/admin/realms/master/users";

    private static final String TOKEN_URL= KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/token";

    private static final String CLIENT_ID= "salon-booking-client";
    private static final String CLIENT_SECRET= "sTij4B2lShU4Mn4uoVuRkyQAoT4d5srv";
    private static final String GRANT_TYPE = "password";
    private static final String scope= "openid profile email";
    private static final String username="dipeshsharma001s";
    private static final String password="Dipesh@001";
    private static final String clientId="c46633b6-5a6d-4637-8453-1640fe1bc5e1";

    private final RestTemplate restTemplate;

    public void createUser(SignupDTO signupDTO) throws Exception{

        String ACCESS_TOKEN=getAdminAccessToken(username,password,GRANT_TYPE,null).getAccessToken();

        System.out.println("Access Token:"+ACCESS_TOKEN);
        Credential credential = new Credential();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(signupDTO.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupDTO.getUsername());
        userRequest.setEmail(signupDTO.getEmail());
        userRequest.setEnabled(true);
        userRequest.setFirstName(signupDTO.getFirstName());
        userRequest.setLastName(signupDTO.getLastName());
        userRequest.setCredentials(credentials);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<String>response= restTemplate.exchange(KEYCLOAK_ADMIN_API, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode()==HttpStatus.CREATED){
            System.out.println("user created successfully");

            KeyCloakUserDTO user=fetchFirstUserByUsername(signupDTO.getUsername(),ACCESS_TOKEN);

            KeyCloakRole role=getRoleByName(clientId,ACCESS_TOKEN,signupDTO.getRole().toString());

            List<KeyCloakRole>roles=new ArrayList<>();
            roles.add(role);

            assignRoleToUser(user.getId(),clientId,roles,ACCESS_TOKEN);
        }
        else {
            throw new Exception("user creation failed");
        }
    }

    public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> requestBody=new LinkedMultiValueMap<>();
        requestBody.add("grant_type",grantType);
        requestBody.add("username",username);
        requestBody.add("password",password);
        requestBody.add("refresh_token",refreshToken);
        requestBody.add("client_id",CLIENT_ID);
        requestBody.add("client_secret",CLIENT_SECRET);
        requestBody.add("scope",scope);
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<TokenResponse>response=restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, TokenResponse.class);

        if (response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return response.getBody();
        }
        else {
            throw new Exception("Failed to obtain access token");
        }
    }

    public KeyCloakRole getRoleByName (String clientId, String token, String role) throws Exception {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/clients/" + clientId + "/roles/" + role;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeyCloakRole> response= restTemplate.exchange(url, HttpMethod.GET, requestEntity, KeyCloakRole.class);

        if (response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return response.getBody();
        }
        else {
            throw new Exception("Failed to obtain role");
        }
    }

    public KeyCloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users?username=" +
                URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeyCloakUserDTO[]> response= restTemplate.exchange(url, HttpMethod.GET, requestEntity, KeyCloakUserDTO[].class);

        KeyCloakUserDTO[] users=response.getBody();

         if (users!=null && users.length>0){
            return users[0];
        }
        else {
            throw new Exception("Failed to fetch user with "+username);
        }
    }

    public void assignRoleToUser(String userId,String clientId,List<KeyCloakRole>roles,String token) throws Exception {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users/" + userId + "/role-mappings/clients/" + clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<KeyCloakRole>> requestEntity = new HttpEntity<>(roles,headers);

        try {
        ResponseEntity<String> response= restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
        }
        catch (Exception e){
            throw  new Exception("Failed to assign role "+e.getMessage());
        }
    }

    public KeyCloakUserDTO fetchUserProfileByJwt(String token) throws Exception {
        String url = KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/userinfo";

        HttpHeaders headers = new HttpHeaders();
        // Ensure we always send "Bearer <token>"
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        headers.set("Authorization",token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KeyCloakUserDTO> response =
                    restTemplate.exchange(url, HttpMethod.GET, requestEntity, KeyCloakUserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            throw new Exception("Failed to get user info " + e.getMessage());
        }
    }
}

