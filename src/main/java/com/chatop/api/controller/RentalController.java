package com.chatop.api.controller;

import com.chatop.api.dto.RentalCreationRequest;
import com.chatop.api.dto.RentalDto;
import com.chatop.api.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<Map<String, List<RentalDto>>> getAllRentals() {
        List<RentalDto> rentals = rentalService.getAllRentals();
        // Le front-end attend un objet avec une clé "rentals" contenant la liste
        return ResponseEntity.ok(Map.of("rentals", rentals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Long id) {
        RentalDto rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @PostMapping(consumes = { "multipart/form-data" }) // Explicite pour la documentation
    public ResponseEntity<Map<String, String>> createRental(
            @RequestParam("picture") MultipartFile picture,
            @ModelAttribute RentalCreationRequest request, // Spring mappe name, surface... automatiquement
            Authentication authentication // Injecté par Spring Security
    ) {
        // On passe l'email de l'utilisateur connecté au service
        rentalService.createRental(request, picture, authentication.getName());

        return ResponseEntity.ok(Map.of("message", "Rental created !"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long id, @ModelAttribute RentalDto rentalDto) {
        rentalService.updateRental(id, rentalDto);
        return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    }

}