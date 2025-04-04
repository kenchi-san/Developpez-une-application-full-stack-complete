package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.UpdateUserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Modifier les informations du compte utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
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
                                    value = "{\n  \"fullName\": \"Jean Dupont\",\n  \"password\": \"newSecurePassword123\"\n}"
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