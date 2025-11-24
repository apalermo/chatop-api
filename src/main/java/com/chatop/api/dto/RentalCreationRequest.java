package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class RentalCreationRequest {
    @Schema(description = "Nom de la location", example = "Appartement vue mer")
    private String name;

    @Schema(description = "Surface en m²", example = "45")
    private BigDecimal surface;

    @Schema(description = "Prix par nuit", example = "150")
    private BigDecimal price;

    @Schema(description = "Description détaillée", example = "Superbe appartement...")
    private String description;

    @Schema(description = "Image de la location (fichier jpg/png)")
    private MultipartFile picture;
}