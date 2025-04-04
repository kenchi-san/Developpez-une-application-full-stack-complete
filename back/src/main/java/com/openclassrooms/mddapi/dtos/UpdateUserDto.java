package com.openclassrooms.mddapi.dtos;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UpdateUserDto {

    @Schema(description = "Nouveau mot de passe de l'utilisateur", example = "newSecurePassword123")
    private String password;

    @Schema(description = "Nouveau nom complet de l'utilisateur", example = "Jean Dupont")
    private String fullName;
}
