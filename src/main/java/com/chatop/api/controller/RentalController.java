package com.chatop.api.controller;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}