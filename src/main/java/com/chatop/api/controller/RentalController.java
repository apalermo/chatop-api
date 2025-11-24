package com.chatop.api.controller;

import com.chatop.api.dto.RentalCreationRequest;
import com.chatop.api.dto.RentalDto;
import com.chatop.api.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "Gestion des locations")
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary = "Lister les locations")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<Map<String, List<RentalDto>>> getAllRentals() {
        List<RentalDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(Map.of("rentals", rentals));
    }

    @Operation(summary = "Détail d'une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location trouvée"),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Long id) {
        RentalDto rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @Operation(summary = "Créer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location créée",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Rental created !\"}"))),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json"))
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createRental(
            @ModelAttribute RentalCreationRequest request,
            Authentication authentication
    ) {
        rentalService.createRental(request, request.getPicture(), authentication.getName());
        return ResponseEntity.ok(Map.of("message", "Rental created !"));
    }

    @Operation(summary = "Modifier une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mise à jour réussie",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Rental updated !\"}"))),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long id, @ModelAttribute RentalDto rentalDto) {
        rentalService.updateRental(id, rentalDto);
        return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    }
}