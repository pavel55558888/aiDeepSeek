package org.example.aideepseek.deepseek.service;

import org.springframework.stereotype.Service;

@Service
public interface DeepSeekService {
    public String getChatCompletion(String userMessage);

}
