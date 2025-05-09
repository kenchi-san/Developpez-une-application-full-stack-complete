package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.article.ThemeDto;
import com.openclassrooms.mddapi.dtos.article.ThemeListDto;
import com.openclassrooms.mddapi.services.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Theme", description = "Gestion des thèmes")
@RestController
@CrossOrigin(maxAge = 3600,origins = "http://localhost:4200")
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Operation(
            summary = "Suivre un thème ou non",
            description = "Permet à un utilisateur connecté de suivre un thème ou d'arrêter en fournissant son ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thème suivi avec succès/ arrêt du suivi"),
            @ApiResponse(responseCode = "500", description = "Erreur interne")
    })
    @PostMapping("/manage-subscribe/{id}")
    public ResponseEntity<String> suiviArticle(@Parameter(description = "ID du thème à suivre ou non", example = "3")
                                               @PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            themeService.manageSubscribe(id, userDetails);
            return ResponseEntity.ok("Thème suivi avec succès !");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur est survenue.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ThemeDto>> getAllThemes() {
        List<ThemeDto> themeList = this.themeService.getAllThemes();
        return ResponseEntity.ok(themeList);
    }
    @GetMapping("/theme-list")
    public ResponseEntity<List<ThemeListDto>> getListThemes() {
        List<ThemeListDto> themeList = this.themeService.getListThemes();
        return ResponseEntity.ok(themeList);
    }

}

