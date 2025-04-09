package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.dtos.user.LoginUserDto;
import com.openclassrooms.mddapi.dtos.user.RegisterUserDto;
import com.openclassrooms.mddapi.responses.LoginResponse;
import com.openclassrooms.mddapi.services.AuthenticationService;
import com.openclassrooms.mddapi.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentification", description = "Endpoints liés à l'inscription et la connexion")
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")  // Autoriser l'accès depuis Angular
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Inscription d'un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur inscrit avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données d'inscription",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterUserDto.class),
                            examples = @ExampleObject(value = "{ \"email\": \"test@example.com\", \"password\": \"Password123!\", \"fullName\": \"Jane Doe\" }")
                    )
            )
            @RequestBody RegisterUserDto registerUserDto) {

        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Connexion utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie, JWT retourné",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données de connexion",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginUserDto.class),
                            examples = @ExampleObject(value = "{ \"email\": \"test@example.com\", \"password\": \"Password123!\" }")
                    )
            )
            @RequestBody LoginUserDto loginUserDto) {

        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
System.out.println("toto"+loginUserDto);
        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
