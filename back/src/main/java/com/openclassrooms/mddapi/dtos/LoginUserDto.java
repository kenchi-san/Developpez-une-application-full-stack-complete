package com.openclassrooms.mddapi.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}