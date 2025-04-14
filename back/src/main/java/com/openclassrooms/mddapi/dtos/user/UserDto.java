package com.openclassrooms.mddapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullName;
}
