package com.salon.categoryservice.service.client;

import com.salon.categoryservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USERSERVICE")
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization")String jwt) throws Exception;

}
