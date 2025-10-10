package org.example.aideepseek.deepseek.service.impl;

import org.example.aideepseek.deepseek.model.DeepSeekRequest;
import org.example.aideepseek.deepseek.model.DeepSeekResponse;
import org.example.aideepseek.deepseek.model.Message;
import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeepSeekServiceImpl implements DeepSeekService {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekServiceImpl.class);
    private final RestTemplate restTemplate;
    private final String deepseekApiUrl;
    private final String apiKey;
    private final String model;
    private final int maxTokens;
    private final double temperature;

    public DeepSeekServiceImpl(
            @Value("${deepseek.api.url}") String deepseekApiUrl,
            @Value("${deepseek.api.key}") String apiKey,
            @Value("${deepseek.api.model}") String model,
            @Value("${deepseek.api.max.tokens}") int maxTokens,
            @Value("${deepseek.api.temperature}") double temperature
     ) {
        this.deepseekApiUrl = deepseekApiUrl;
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;

        log.debug("DeepSeek API URL: {}", deepseekApiUrl);
    }

    @Override
    public String getChatCompletion(String userMessage) {
        log.debug("Получено сообщение от пользователя: {}", userMessage);

        DeepSeekRequest request = new DeepSeekRequest(
                model,
                List.of(new Message("user", userMessage)),
                temperature,
                maxTokens
        );

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<DeepSeekResponse> response = restTemplate.exchange(
                    deepseekApiUrl,
                    HttpMethod.POST,
                    entity,
                    DeepSeekResponse.class
            );

            DeepSeekResponse responseBody = response.getBody();
            if (responseBody != null &&
                    responseBody.getChoices() != null &&
                    !responseBody.getChoices().isEmpty() &&
                    responseBody.getChoices().get(0).getMessage() != null) {

                String content = responseBody.getChoices().get(0).getMessage().getContent();
                log.debug("Успешный ответ от DeepSeek: {}", content);
                return content;
            }

            log.warn("Получен некорректный ответ от DeepSeek: {}", responseBody);
            return "Не удалось получить ответ от DeepSeek. Некорректный формат ответа.";

        } catch (org.springframework.web.client.HttpClientErrorException ex) {
            log.error("Ошибка API DeepSeek (HTTP {}): {}", ex.getStatusCode(), ex.getResponseBodyAsString());

            String errorMessage;
            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                errorMessage = ex.getStatusCode() + " Неверный API-ключ. \n"
                        + "Ошибка: API DeepSeek HTTP " + ex.getStatusCode() + "   " + ex.getResponseBodyAsString();
            } else if (ex.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                errorMessage = ex.getStatusCode() + " Слишком много запросов. Попробуйте позже.\n"
                        + "Ошибка: API DeepSeek HTTP " + ex.getStatusCode() + "   " + ex.getResponseBodyAsString();
            } else if (ex.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
                errorMessage = ex.getStatusCode() + " Лимит запросов исчерпан. Необходима оплата.\n"
                        + "Ошибка: API DeepSeek HTTP " + ex.getStatusCode() + "   " + ex.getResponseBodyAsString();
            } else {
                errorMessage = ex.getStatusCode() + " Ошибка в запросе к сервису.\n"
                        + "Ошибка: API DeepSeek HTTP " + ex.getStatusCode() + "   " + ex.getResponseBodyAsString();
            }
            throw new RuntimeException("Ошибка: " + errorMessage);

        } catch (org.springframework.web.client.HttpServerErrorException ex) {
            log.error("Ошибка сервера DeepSeek (HTTP {}): {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new RuntimeException("Ошибка: Сервис временно недоступен.");

        } catch (org.springframework.web.client.ResourceAccessException ex) {
            log.error("Сетевая ошибка при обращении к DeepSeek: {}", ex.getMessage());
            throw new RuntimeException("Ошибка: Сервис временно недоступен. Проверьте соединение.");

        } catch (Exception ex) {
            log.error("Неожиданная ошибка при работе с DeepSeek: {}", ex.getMessage());
            throw new RuntimeException("Ошибка: Внутренняя ошибка сервиса.");
        }
    }
}