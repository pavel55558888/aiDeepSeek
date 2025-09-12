package org.example.aideepseek.deepseek.controller;

import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat/v1")
public class ChatController {

    private final DeepSeekService deepSeekService;

    public ChatController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> chat(@RequestBody String userMessage) {
        return deepSeekService.getChatCompletion(userMessage)
                .map(content -> ResponseEntity.ok().body(content))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(500).body("Ошибка: " + ex.getMessage())));
    }
}

