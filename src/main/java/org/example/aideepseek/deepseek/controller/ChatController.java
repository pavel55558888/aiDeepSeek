package org.example.aideepseek.deepseek.controller;

import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/v1")
public class ChatController {

    private final DeepSeekService deepSeekService;

    public ChatController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody String userMessage) {
        try {
            String response = deepSeekService.getChatCompletion(userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}