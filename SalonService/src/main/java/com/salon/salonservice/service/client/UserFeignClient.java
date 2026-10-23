package com.salon.salonservice.service.client;

import com.salon.salonservice.payload.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("USERSERVICE")
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization")String jwt) throws Exception;

    @PutMapping("/api/users/{id}/role")
    ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestParam String role);

}
