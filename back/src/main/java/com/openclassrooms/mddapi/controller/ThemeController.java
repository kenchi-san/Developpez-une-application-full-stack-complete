package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.services.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Operation(
            summary = "Suivre un thème",
            description = "Permet à un utilisateur connecté de suivre un thème en fournissant son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thème suivi avec succès"),
            @ApiResponse(responseCode = "400", description = "L'utilisateur suit déjà ce thème ou la requête est invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PostMapping("/follow/{id}")
    public ResponseEntity<String> suiviArticle(@Parameter(description = "ID du thème à suivre", example = "3")
                                               @PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            themeService.followTheme(id, userDetails);
            return ResponseEntity.ok("Thème suivi avec succès !");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur est survenue.");
        }
    }

    @Operation(
            summary = "Ne plus suivre un thème",
            description = "Permet à un utilisateur connecté d'arrêter de suivre un thème en fournissant son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Désabonnement du thème réussi"),
            @ApiResponse(responseCode = "400", description = "L'utilisateur ne suit pas ce thème ou la requête est invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity<String> unfollowTheme(@Parameter(description = "ID du thème à ne plus suivre", example = "3")
                                                @PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            themeService.unFollowTheme(id, userDetails);
            return ResponseEntity.ok("Vous ne suivez plus ce thème.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur est survenue.");
        }
    }

}

