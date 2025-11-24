package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    @Schema(description = "Identifiant unique de l'utilisateur", example = "1")
    private Long id;

    @Schema(description = "Nom complet", example = "Jean Dupont")
    private String name;

    @Schema(description = "Adresse email", example = "jean.dupont@test.com")
    private String email;

    @Schema(description = "Date de création du compte", example = "2024-01-01T10:00:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière mise à jour", example = "2024-01-01T10:00:00")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}