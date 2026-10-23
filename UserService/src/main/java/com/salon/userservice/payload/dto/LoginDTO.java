package com.salon.userservice.payload.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;
    private String password;
}
