package org.example.aideepseek.controller;

import org.example.aideepseek.controller.service.ChatControllerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/v1")
public class ChatController {

    @Autowired
    private ChatControllerService chatControllerService;

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);


    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody String userMessage,
            @RequestHeader(value = "format") String format,
            @RequestHeader(value = "domainHtml") String domain) {

        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest().body("User message is required");
        }
        if (format == null || format.isBlank()) {
            return ResponseEntity.badRequest().body("Header 'format' is required");
        }
        if (domain == null || domain.isBlank()) {
            return ResponseEntity.badRequest().body("Header 'domainHtml' is required");
        }

        try {
            if ("html".equals(format)) {
                return chatControllerService.handleHtmlFormat(userMessage, domain);
            } else if ("question".equals(format)) {
                return chatControllerService.handleQuestionFormat(userMessage);
            } else {
                return ResponseEntity.badRequest().body("Unsupported format: " + format);
            }
        } catch (Exception e) {
            log.error("Unexpected error in chat endpoint", e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

}