package com.chatop.api.service;

import com.chatop.api.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Génère le constructeur avec les arguments "final"
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        // On transforme le DTO en Entité (Mapping manuel)
        String email = request.getEmail();
        User user = new User();
        user.setEmail(email);
        user.setName(request.getName());

        // On crypte le mot de passe (Impératif de sécurité)
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // On sauvegarde en base
        userRepository.save(user);

        return jwtService.generateToken(email);
    }


    public String login(LoginRequest request) {
        // On demande à Spring Security de vérifier les infos
        // Si ça échoue, ça lancera une exception (BadCredentialsException) automatiquement
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Si on passe l'étape 1, on génère le token.
        return jwtService.generateToken(authentication);
    }
}