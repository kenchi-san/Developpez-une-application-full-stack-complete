package com.openclassrooms.mddapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthorDto {
    @Schema(description = "ID de l'auteur")
    private long id;
    @Schema(description = "Auteur de l'article")
    private String fullName;

}
