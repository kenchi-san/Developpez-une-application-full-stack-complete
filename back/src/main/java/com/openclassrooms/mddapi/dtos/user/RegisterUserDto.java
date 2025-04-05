package com.openclassrooms.mddapi.dtos.user;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;
}