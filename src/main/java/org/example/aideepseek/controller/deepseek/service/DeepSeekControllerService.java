package org.example.aideepseek.controller.deepseek.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DeepSeekControllerService {
    public ResponseEntity<?> handleHtmlFormat(String rawHtml, String domain);
    public ResponseEntity<?> handleQuestionFormat(String userMessage);
}
