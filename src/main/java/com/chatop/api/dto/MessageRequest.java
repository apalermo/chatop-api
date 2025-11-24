package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {

    @NotBlank
    @Schema(description = "Contenu du message", example = "Bonjour, est-ce disponible ?")
    private String message;

    @NotNull
    @JsonProperty("user_id")
    @Schema(description = "ID de l'utilisateur", example = "1")
    private Long userId;

    @NotNull
    @JsonProperty("rental_id")
    @Schema(description = "ID de la location", example = "1")
    private Long rentalId;
}