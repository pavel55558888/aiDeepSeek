package org.example.aideepseek.controller.deepseek;

import org.example.aideepseek.controller.deepseek.service.DeepSeekControllerService;
import org.example.aideepseek.prompt.PromptDeepSeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/v1")
public class DeepSeekController {
    @Value("${domain.white.list.is}")
    private boolean isWhiteList;
    @Value("${domain.white.list}")
    private List<String> whiteList;

    @Autowired
    private DeepSeekControllerService deepSeekControllerService;

    private static final Logger log = LoggerFactory.getLogger(DeepSeekController.class);


    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody String userMessage,
            @RequestHeader(value = "format") String format,
            @RequestHeader(value = "domainHtml") String domain) {

        if (isWhiteList && !whiteList.contains(domain)) {
            return ResponseEntity.badRequest()
                    .body("This site is not whitelisted");
        }

        try {

            if ("html".equals(format)) {
                if (userMessage == null || userMessage.isBlank()) {
                    return ResponseEntity.badRequest().body("User message is blank");
                }
                log.debug("html");
                return deepSeekControllerService.handleHtmlFormat(userMessage, domain);
            } else if ("question".equals(format)) {
                if (userMessage == null || userMessage.isBlank()) {
                    return ResponseEntity.badRequest().body("User message is blank");
                }
                log.debug("question");
                return deepSeekControllerService.handleQuestionFormat(PromptDeepSeek.DEEPSEEK_PROMPT_QUESTION.getPrompt() + userMessage);
            }  else {
                return ResponseEntity.badRequest().body("Unsupported format: " + format);
            }

        } catch (Exception e) {
            log.error("Unexpected error in chat endpoint", e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

}