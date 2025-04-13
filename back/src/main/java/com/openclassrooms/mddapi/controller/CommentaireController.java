package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.commentaire.CommentaireDto;
import com.openclassrooms.mddapi.models.Commentaire;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CommentaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentaire")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:4200")
@Tag(name = "Commentaire", description = "Gestion des commentaires")
public class CommentaireController {


    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @Operation(
            summary = "Ajouter un commentaire à un article",
            description = "Permet à un utilisateur connecté de créer un commentaire sur un article spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaire ajouté avec succès",
                    content = @Content(schema = @Schema(implementation = Commentaire.class))),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content)
    })
    @PostMapping("/create/{id}")
    public ResponseEntity<?> addCommentaireToArticle(
            @PathVariable(name = "id") Long articleId,
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Contenu du commentaire",
                    content = @Content(schema = @Schema(implementation = CommentaireDto.class))
            ) CommentaireDto dto,
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
