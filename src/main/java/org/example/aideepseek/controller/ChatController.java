package org.example.aideepseek.controller;


import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.example.aideepseek.ignite.IgniteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/v1")
public class ChatController {
    private Logger log = LoggerFactory.getLogger(ChatController.class);

    private final DeepSeekService deepSeekService;

    private final IgniteService igniteService;

    public ChatController(DeepSeekService deepSeekService, IgniteService igniteService) {
        this.deepSeekService = deepSeekService;
        this.igniteService = igniteService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody String userMessage) {
        log.info(userMessage);

        var taskCache = igniteService.getTask(userMessage);

        if (taskCache != null) {
            return ResponseEntity.ok(taskCache);
        }

        try {
            String response = deepSeekService.getChatCompletion(userMessage);
            igniteService.cacheTask(userMessage, response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}