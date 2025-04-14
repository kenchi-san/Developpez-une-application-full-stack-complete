package com.openclassrooms.mddapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MeDto {
    @Schema(description = "email de l'utilisateur", example = "test@exemple.com")
    private String email;

    @Schema(description = "Nouveau nom complet de l'utilisateur", example = "Jean Dupont")
    private String fullName;
}
