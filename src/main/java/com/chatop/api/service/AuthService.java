package com.chatop.api.service;

import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Génère le constructeur avec les arguments "final"
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        // On transforme le DTO en Entité (Mapping manuel)
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        // On crypte le mot de passe (Impératif de sécurité)
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // On sauvegarde en base
        userRepository.save(user);
    }
}