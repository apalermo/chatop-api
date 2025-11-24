package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RentalDto {
    @Schema(description = "Identifiant unique de la location", example = "1")
    private Long id;

    @Schema(description = "Nom de la location", example = "Appartement vue mer")
    private String name;

    @Schema(description = "Surface en m²", example = "45")
    private BigDecimal surface;

    @Schema(description = "Prix par nuit", example = "150")
    private BigDecimal price;

    @Schema(description = "URL de l'image", example = "http://localhost:3001/api/images/1.jpg")
    private String picture;

    @Schema(description = "Description détaillée", example = "Magnifique appartement...")
    private String description;

    @Schema(description = "Identifiant du propriétaire", example = "1")
    @JsonProperty("owner_id")
    private Long ownerId;

    @Schema(description = "Date de création", example = "2024-01-01T12:00:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière modification", example = "2024-01-02T12:00:00")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}