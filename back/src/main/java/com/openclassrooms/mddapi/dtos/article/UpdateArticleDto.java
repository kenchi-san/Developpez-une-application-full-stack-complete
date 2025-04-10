package com.openclassrooms.mddapi.dtos.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UpdateArticleDto {

    @Schema(description = "Titre de l'article", example = "Introduction à Spring Boot")
    private String title;

    @Schema(description = "Contenu de l'article", example = "Spring Boot simplifie le développement Java...")
    private String content;

    @Schema(description = "Thème auquel l'article appartient")
    private ThemeDto theme;

    @Schema(description = "Date de mise à jour de l'article (avec fuseau horaire)", example = "2025-04-04T17:09:08+02:00")
    private ZonedDateTime updated;

}
