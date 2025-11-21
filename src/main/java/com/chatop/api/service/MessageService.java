package com.chatop.api.service;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void createMessage(MessageRequest request) {
        // Vérification de l'existence (recommandé pour la robustesse)
        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));


        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setUserId(request.getUserId());
        message.setRentalId(request.getRentalId());


        messageRepository.save(message);
    }
}