package com.chatop.api.service;

import com.chatop.api.dto.RentalCreationRequest;
import com.chatop.api.dto.RentalDto;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public RentalDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found")); // Gestion simple pour l'instant
        return mapToDto(rental);
    }

    public void updateRental(Long id, RentalDto rentalDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // On met à jour uniquement les champs modifiables
        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());

        // Note : On ne touche pas à l'image, au propriétaire ou à la date de création ici

        rentalRepository.save(rental);
    }




    public void createRental(RentalCreationRequest request, MultipartFile picture, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Sauvegarder l'image sur le disque
        String pictureUrl;
        try {
            pictureUrl = saveFile(picture);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        // 2. Créer l'entité avec la vraie URL (ou le chemin relatif)
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setPicture(pictureUrl);
        rental.setOwnerId(user.getId());

        rentalRepository.save(rental);
    }

    // Méthode utilitaire pour sauvegarder le fichier
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }

        // Créer le dossier s'il n'existe pas
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Générer un nom de fichier unique (pour éviter d'écraser si deux fichiers ont le même nom)
        // Ici on garde le nom d'origine pour simplifier, mais en prod on utiliserait un UUID
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Copier le fichier
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Retourner l'URL relative (accessible via http://localhost:3001/images/nom_fichier)
        // Note : Pour que ça marche, il faut que Spring soit configuré pour servir les ressources statiques
        return "http://localhost:3001/api/images/" + fileName;
    }

    // Méthode utilitaire pour transformer l'entité en DTO
    private RentalDto mapToDto(Rental rental) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwnerId());
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());
        return dto;
    }
}