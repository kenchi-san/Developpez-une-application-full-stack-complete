package com.openclassrooms.mddapi.dtos.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    @Schema(description = "Identifiant unique de l'article", example = "1")
    private long id;

    @Schema(description = "Titre de l'article", example = "Introduction à Spring Boot")
    private String title;

    @Schema(description = "Contenu de l'article", example = "Spring Boot simplifie le développement Java...")
    private String content;

    @Schema(description = "Auteur de l'article")
    private AuthorDto author;

    @Schema(description = "Date de création de l'article (avec fuseau horaire)", example = "2025-04-04T17:08:54+02:00")
    private ZonedDateTime created;

    @Schema(description = "Date de mise à jour de l'article (avec fuseau horaire)", example = "2025-04-04T17:09:08+02:00")
    private ZonedDateTime updated;

    @Schema(description = "Thème auquel l'article appartient")
    private ThemeDto theme;

}
