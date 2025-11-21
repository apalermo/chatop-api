package com.chatop.api.controller;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageRequest request) {
        messageService.createMessage(request);
        return ResponseEntity.ok(Map.of("message", "Message send with success"));
    }
}