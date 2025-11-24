package com.chatop.api.controller;

import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserDto;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Gestion de l'authentification")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Inscription", description = "Crée un compte utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"token\":\"jwt\"}"))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Le format de l'email est invalide\"}")))
    })
    @SecurityRequirements()
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "Connexion", description = "Authentifie un utilisateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"token\":\"jwt\"}"))),
            @ApiResponse(responseCode = "401", description = "Erreur d'authentification",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Les identifications sont erronées\"}")))
    })
    @SecurityRequirements()
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "Profil utilisateur", description = "Récupère l'utilisateur connecté.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = authService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(userDto);
    }
}