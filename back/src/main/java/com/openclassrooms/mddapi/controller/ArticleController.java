package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.article.ArticleDto;
import com.openclassrooms.mddapi.dtos.article.CreateArticleDto;
import com.openclassrooms.mddapi.dtos.article.UpdateArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:4200")
@Tag(name = "Article", description = "Endpoints liés à la gestion des articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(
            summary = "Récupérer la liste de tous les articles",
            description = "Cette méthode retourne une liste de tous les articles existants."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des articles retournée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleDto.class),
                            examples = @ExampleObject(value = "[\n" +
                                    "  {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"title\": \"Introduction à Spring Boot\",\n" +
                                    "    \"content\": \"Spring Boot simplifie le développement Java...\",\n" +
                                    "    \"author\": {\n" +
                                    "      \"id\": 9007199254740991,\n" +
                                    "      \"fullName\": \"Jean Dupont\"\n" +
                                    "    },\n" +
                                    "    \"created\": \"2025-04-04T17:08:54+02:00\",\n" +
                                    "    \"updated\": \"2025-04-04T17:09:08+02:00\",\n" +
                                    "    \"theme\": {\n" +
                                    "      \"id\": 9007199254740991,\n" +
                                    "      \"name\": \"Spring Boot\"\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "]")
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"message\": \"Une erreur interne est survenue. Veuillez réessayer plus tard.\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "403",
                    description = "Accès interdit. L'utilisateur n'a pas les droits nécessaires pour accéder à cette ressource.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 403,\n" +
                                    "  \"message\": \"Accès interdit : Vous n'avez pas les droits nécessaires pour accéder à cette ressource.\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Ressource non trouvée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"message\": \"La ressource demandée n'a pas été trouvée.\"\n" +
                                    "}")
                    )
            )
    })
    @GetMapping("/")
    public ResponseEntity<List<ArticleDto>> getListArticle() {
        List<ArticleDto> articles = articleService.allArticle();
        return ResponseEntity.ok(articles);
    }

    @Operation(
            summary = "Récupérer un article par ID",
            description = "Cette méthode retourne un article basé sur son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Article trouvé et retourné",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleDto.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"id\": 1,\n" +
                                    "  \"title\": \"Introduction à Spring Boot\",\n" +
                                    "  \"content\": \"Spring Boot simplifie le développement Java...\",\n" +
                                    "  \"author\": {\n" +
                                    "    \"id\": 9007199254740991,\n" +
                                    "    \"fullName\": \"Jean Dupont\"\n" +
                                    "  },\n" +
                                    "  \"created\": \"2025-04-04T17:08:54+02:00\",\n" +
                                    "  \"updated\": \"2025-04-04T17:09:08+02:00\",\n" +
                                    "  \"theme\": {\n" +
                                    "    \"id\": 9007199254740991,\n" +
                                    "    \"name\": \"Spring Boot\"\n" +
                                    "  }\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Article non trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"message\": \"L'article demandé n'a pas été trouvé.\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"message\": \"Une erreur interne est survenue. Veuillez réessayer plus tard.\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "403",
                    description = "Accès interdit. L'utilisateur n'a pas les droits nécessaires pour accéder à cette ressource.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"status\": 403,\n" +
                                    "  \"message\": \"Accès interdit : Vous n'avez pas les droits nécessaires pour accéder à cette ressource.\"\n" +
                                    "}")
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") Long id) {
        ArticleDto article = articleService.getArticleById(id);

        if (article == null) {
            return ResponseEntity.notFound().build(); // Si l'article n'est pas trouvé
        }

        return ResponseEntity.ok(article);
    }

    @Operation(
            summary = "Créer un nouvel article",
            description = "Cette méthode permet de créer un article à partir des données fournies : titre, contenu, auteur et thème.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données de l'article à créer",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemple de création",
                                    summary = "Payload pour créer un article",
                                    value = """
                                            {
                                              "title": "Introduction à Spring Boot",
                                              "content": "Spring Boot simplifie le développement Java en permettant de créer des applications avec une configuration minimale.",
                                              "author": {
                                                "id": 1
                                              },
                                              "theme": {
                                                "id": 2
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Article créé avec succès",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Article créé",
                                    summary = "Article retourné après création",
                                    value = """
                                            {
                                              "id": 10,
                                              "title": "Introduction à Spring Boot",
                                              "content": "Spring Boot simplifie le développement Java en permettant de créer des applications avec une configuration minimale.",
                                              "created": "2025-04-04T17:08:54+02:00",
                                              "updated": "2025-04-04T17:08:54+02:00",
                                              "author": {
                                                "id": 1,
                                                "fullName": "Jean Dupont"
                                              },
                                              "theme": {
                                                "id": 2,
                                                "name": "Java"
                                              }
                                            }
                                            """
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requête invalide",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 400",
                                    summary = "Champs manquants ou invalides",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Le champ 'title' est requis.",
                                              "instance": "/article/create"
                                            }
                                            """
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Accès interdit",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 403",
                                    summary = "Utilisateur non autorisé",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Forbidden",
                                              "status": 403,
                                              "detail": "Vous n’avez pas les droits pour effectuer cette action.",
                                              "instance": "/article/create"
                                            }
                                            """
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Auteur ou thème non trouvé",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 404",
                                    summary = "Thème introuvable",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Le thème avec l’ID 99 est introuvable.",
                                              "instance": "/article/create"
                                            }
                                            """
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 500",
                                    summary = "Erreur SQL",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Internal Server Error",
                                              "status": 500,
                                              "detail": "could not execute statement [Duplicate entry '1' for key 'article.PRIMARY']",
                                              "instance": "/article/create"
                                            }
                                            """
                            )
                    })
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ArticleDto> addNewArticle(@RequestBody CreateArticleDto article) {

        ArticleDto newArticle = articleService.newArticle(article);
        if (newArticle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(newArticle);
    }


    @Operation(
            summary = "Met à jour un article existant",
            description = "Cette méthode permet de mettre à jour un article en fonction de l'ID spécifié. "
                    + "Si l'article n'existe pas ou si des données invalides sont envoyées, une erreur appropriée sera renvoyée."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Article mis à jour",
                                            summary = "Article retourné après mise à jour",
                                            value = """
                                                    {
                                                      "id": 10,
                                                      "title": "Introduction à Spring Boot",
                                                      "content": "Spring Boot simplifie le développement Java...",
                                                      "created": "2025-04-04T17:08:54+02:00",
                                                      "updated": "2025-04-04T17:09:08+02:00",
                                                      "author": {
                                                        "id": 1,
                                                        "fullName": "Jean Dupont"
                                                      },
                                                      "theme": {
                                                        "id": 2,
                                                        "name": "Java"
                                                      }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides dans la requête",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erreur 400",
                                            summary = "Champs manquants ou invalides",
                                            value = """
                                                    {
                                                      "type": "about:blank",
                                                      "title": "Bad Request",
                                                      "status": 400,
                                                      "detail": "Le champ 'title' est requis ou invalide.",
                                                      "instance": "/article/update/{id}"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article introuvable",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erreur 404",
                                            summary = "Article non trouvé",
                                            value = """
                                                    {
                                                      "type": "about:blank",
                                                      "title": "Not Found",
                                                      "status": 404,
                                                      "detail": "L'article avec l'ID spécifié est introuvable.",
                                                      "instance": "/article/update/{id}"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Accès interdit",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erreur 403",
                                            summary = "Utilisateur non autorisé",
                                            value = """
                                                    {
                                                      "type": "about:blank",
                                                      "title": "Forbidden",
                                                      "status": 403,
                                                      "detail": "Vous n’avez pas les droits pour effectuer cette action.",
                                                      "instance": "/article/update/{id}"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erreur 500",
                                            summary = "Erreur serveur",
                                            value = """
                                                    {
                                                      "type": "about:blank",
                                                      "title": "Internal Server Error",
                                                      "status": 500,
                                                      "detail": "Erreur lors de la mise à jour de l'article en base de données.",
                                                      "instance": "/article/update/{id}"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Données de l'article à mettre à jour",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Corps de requête - mise à jour d'article",
                            summary = "Exemple de payload pour mise à jour",
                            value = """
                                    {
                                      "title": "Découverte de Spring Boot toto",
                                      "content": "Spring Boot permet de démarrer un projet Java rapidement.",
                                      "author": {
                                        "id": int
                                      },
                                      "theme": {
                                        "id": int
                                      }
                                    }
                                    """
                    )
            )
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDto> updateArticle(
            @PathVariable("id") Long id,
            @org.springframework.web.bind.annotation.RequestBody UpdateArticleDto article) {

        ArticleDto updatedArticle = articleService.updateArticle(id, article);
        return ResponseEntity.ok(updatedArticle);
    }


    @Operation(
            summary = "Supprimer un article",
            description = "Cette méthode permet de supprimer un article de la base de données en fournissant son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article supprimé avec succès",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Succès de suppression",
                                    summary = "Article supprimé avec succès",
                                    value = "\"Article supprimé avec succès\""
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article non trouvé",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 404",
                                    summary = "Article non trouvé",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Not Found",
                                              "status": 404,
                                              "detail": "Article non trouvé",
                                              "instance": "/article/delete/1"
                                            }
                                            """
                            )
                    })
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Erreur 500",
                                    summary = "Erreur de suppression",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Internal Server Error",
                                              "status": 500,
                                              "detail": "Une erreur est survenue lors de la suppression de l'article.",
                                              "instance": "/article/delete/1"
                                            }
                                            """
                            )
                    })
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok("Article supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article non trouvé");
        }
    }
}
