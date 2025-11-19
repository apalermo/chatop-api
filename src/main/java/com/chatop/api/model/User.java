package com.chatop.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data // Lombok génère les getters, setters, equals, hashCode et toString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément
    private Long id;

    @Column(unique = true, nullable = false) // L'email unique et non null
    private String email;

    private String name;

    private String password; // Contiendra le hash BCrypt (jamais le mot de passe en clair)

    @CreationTimestamp // Gère la date automatiquement à l'insertion
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Gère la date automatiquement à la modification
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}