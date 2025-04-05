package com.openclassrooms.mddapi.dtos.user;

import com.openclassrooms.mddapi.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {

    @Email(message = "Email invalide")
    @NotBlank
    private String email;

    @ValidPassword
    private String password;

    @NotBlank(message = "Le nom est requis")
    @Schema(description = "Mot de passe sécurisé (min 8 caractères, majuscule, minuscule, chiffre, caractère spécial)")
    private String fullName;
}