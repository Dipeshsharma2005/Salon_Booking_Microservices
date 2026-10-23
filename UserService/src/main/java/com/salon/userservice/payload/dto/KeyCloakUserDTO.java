package com.salon.userservice.payload.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KeyCloakUserDTO {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
