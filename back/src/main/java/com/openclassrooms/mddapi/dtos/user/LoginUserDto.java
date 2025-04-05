package com.openclassrooms.mddapi.dtos.user;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}