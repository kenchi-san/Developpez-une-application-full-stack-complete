package com.openclassrooms.mddapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateArticleDto {

    @Schema(description = "Titre de l'article", example = "Introduction à Spring Boot")
    private String title;

    @Schema(description = "Contenu de l'article", example = "Spring Boot simplifie le développement Java...")
    private String content;

    private AuthorDto author;

    private ThemeDto theme;

    @Schema(description = "Date de création de l'article (avec fuseau horaire)", example = "2025-04-04T17:08:54+02:00")
    private ZonedDateTime created;

    @Schema(description = "Date de mise à jour de l'article (avec fuseau horaire)", example = "2025-04-04T17:09:08+02:00")
    private ZonedDateTime updated;


}
