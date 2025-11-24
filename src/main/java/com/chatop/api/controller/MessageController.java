package com.chatop.api.controller;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Messages", description = "Messagerie")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Envoyer un message")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message envoyé",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\":\"Message sent with success\"}"))),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Non autorisé", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageRequest request) {
        messageService.createMessage(request);
        return ResponseEntity.ok(Map.of("message", "Message sent with success"));
    }
}