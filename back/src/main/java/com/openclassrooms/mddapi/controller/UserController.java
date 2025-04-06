package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.user.MeDto;
import com.openclassrooms.mddapi.dtos.user.UpdateUserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "User", description = "Endpoints liés à la gestion des utilisateurs")
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Obtenir les informations de l'utilisateur authentifié",
            description = "Cette méthode permet de récupérer les informations (nom et email) de l'utilisateur actuellement connecté."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur authentifié avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MeDto.class),
                            examples = @ExampleObject(
                                    name = "Utilisateur authentifié",
                                    summary = "Informations de l'utilisateur connecté",
                                    value = """
                        {
                          "email": "test@exemple.com",
                          "fullName": "Jean Dupont"
                        }
                        """
                            )
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
    })
    @GetMapping("/me")
    public ResponseEntity<MeDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        MeDto MeDto = new MeDto();
        MeDto.setFullName(currentUser.getFullName());
        MeDto.setEmail(currentUser.getEmail());

        return ResponseEntity.ok(MeDto);
    }

    @Operation(summary = "Obtenir tous les utilisateurs",
            description = "Cette méthode permet de récupérer la liste de tous les utilisateurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content),@ApiResponse(responseCode = "403",
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
    })
    @GetMapping("/")
    public ResponseEntity<List<MeDto>> allUsers() {
        List<MeDto> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Modifier les informations du compte utilisateur",
            description = "Cette méthode permet de modifier les informations de l'utilisateur. Les champs non renseignés ne seront pas modifiés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Non autorisé (JWT requis ou invalide)", content = @Content),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<User> editUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données à modifier (laisser vide un champ pour ne pas le modifier)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUserDto.class),
                            examples = @ExampleObject(
                                    value = "{\n  \"fullName\": \"Jean Dupont\",\n  \"password\": \"newSecurePassword123\",\n  \"email\": \"jean.dupont@example.com\"\n}"
                            )
                    )
            )
            @RequestBody UpdateUserDto updateUserDto,

            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User updatedUser = userService.updateUser(userDetails.getUsername(), updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
}
