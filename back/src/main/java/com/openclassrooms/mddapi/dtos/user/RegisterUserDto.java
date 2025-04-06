package com.openclassrooms.mddapi.dtos.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class RegisterUserDto {

    private String email;

    private String password;

    private String fullName;
}
