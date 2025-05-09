package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.commentaire.CommentaireDto;
import com.openclassrooms.mddapi.models.Commentaire;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CommentaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaire")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:4200")
@Tag(name = "Commentaire", description = "Gestion des commentaires")
public class CommentaireController {


    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @Operation(
            summary = "Récupérer les commentaires d’un article",
            description = "Retourne la liste de tous les commentaires associés à un article en fonction de son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Commentaires trouvés avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commentaire.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aucun commentaire trouvé pour l'article donné",
                    content = @Content
            )
    })
    @GetMapping("/listCommentaire/{id}")
    public ResponseEntity<List<CommentaireDto>> getListCommentaire(
            @Parameter(description = "ID de l'article", required = true)
            @PathVariable Long id) {
        List<CommentaireDto> listCommentaire = commentaireService.getCommentaireByIdArticle(id);
        return ResponseEntity.ok(listCommentaire);
    }

    @PostMapping("/create/{id}")
    @Operation(
            summary = "Ajouter un commentaire à un article",
            description = "Permet à un utilisateur authentifié d'ajouter un commentaire à un article spécifié par son ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de l'article auquel ajouter le commentaire",
                            required = true,
                            example = "1"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Contenu du commentaire à ajouter",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{ \"comment\": \"C'est un excellent article !\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Commentaire ajouté avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentaireDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Article non trouvé"),
                    @ApiResponse(responseCode = "500", description = "Erreur interne")
            }
    )
    public ResponseEntity<CommentaireDto> addCommentaireToArticle(
            @PathVariable(name = "id") Long articleId,
            @RequestBody CommentaireDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            CommentaireDto created = commentaireService.createCommentaire(articleId, dto, currentUser);
            return ResponseEntity.ok(created);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
