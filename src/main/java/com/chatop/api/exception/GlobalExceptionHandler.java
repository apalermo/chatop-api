package com.chatop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice // Transforme cette classe en gestionnaire global
public class GlobalExceptionHandler {

    // Intercepte les erreurs de login (401)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        // On renvoie un JSON vide ou avec le message d'erreur retourné par l'exception
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
    }

    // Gestion des erreurs de validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // On récupère le premier message d'erreur pour faire simple
        String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        if (errorMessage == null) errorMessage = "Erreur de validation"; // pour éviter les erreurs en retournant null

        // On renvoie une 400 Bad Request avec le message
        return ResponseEntity.badRequest().body(Map.of("message", errorMessage));
    }
}