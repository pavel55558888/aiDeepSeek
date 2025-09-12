package org.example.aideepseek.deepseek.service;

import org.example.aideepseek.deepseek.model.DeepSeekRequest;
import org.example.aideepseek.deepseek.model.DeepSeekResponse;
import org.example.aideepseek.deepseek.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class DeepSeekService {
    private final Logger log;
    private final WebClient webClient;

    public DeepSeekService(
            WebClient.Builder webClientBuilder,
            @Value("${deepseek.api.url}") String deepseekApiUrl,
            @Value("${deepseek.api.key}") String apiKey
    ) {
        this.log = LoggerFactory.getLogger(DeepSeekService.class);
        this.webClient = webClientBuilder
                .baseUrl(deepseekApiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        log.info("DeepSeek API URL: " + deepseekApiUrl);
    }

    public Mono<String> getChatCompletion(String userMessage) {

        log.debug(userMessage);

        DeepSeekRequest request = new DeepSeekRequest(
                "deepseek-chat",
                List.of(new Message("user", userMessage)), // История сообщений
                0.7, // "Творчество" модели
                1000 // Макс. количество токенов в ответе
        );

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DeepSeekResponse.class)
                .map(response -> {
                    if (response != null &&
                            response.getChoices() != null &&
                            !response.getChoices().isEmpty() &&
                            response.getChoices().get(0).getMessage() != null) {

                        String content = response.getChoices().get(0).getMessage().getContent();
                        log.debug("Успешный ответ от DeepSeek: {}", content);
                        return content;
                    }
                    log.warn("Получен некорректный ответ от DeepSeek: {}", response);
                    return "Не удалось получить ответ от DeepSeek. Некорректный формат ответа.";
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Обработка HTTP ошибок (4xx, 5xx)
                    log.error("Ошибка API DeepSeek. Status: {}, Body: {}",
                            ex.getStatusCode(), ex.getResponseBodyAsString());

                    String errorMessage;
                    if (ex.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
                        errorMessage = "Лимит запросов исчерпан. Необходима оплата.";
                    } else if (ex.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        errorMessage = "Слишком много запросов. Попробуйте позже.";
                    } else if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        errorMessage = "Неверный API-ключ.";
                    } else if (ex.getStatusCode().is4xxClientError()) {
                        errorMessage = "Ошибка в запросе к сервису.";
                    } else if (ex.getStatusCode().is5xxServerError()) {
                        errorMessage = "Сервис временно недоступен.";
                    } else {
                        errorMessage = "Ошибка при обращении к сервису.";
                    }

                    return Mono.just("Ошибка: " + errorMessage);
                })
                .onErrorResume(WebClientRequestException.class, ex -> {
                    // Обработка сетевых ошибок (таймауты, проблемы с соединением)
                    log.error("Сетевая ошибка при обращении к DeepSeek: {}", ex.getMessage());
                    return Mono.just("Ошибка: Сервис временно недоступен. Проверьте соединение.");
                })
                .onErrorResume(Exception.class, ex -> {
                    // Общая обработка всех остальных исключений
                    log.error("Неожиданная ошибка при работе с DeepSeek: {}", ex.getMessage());
                    return Mono.just("Ошибка: Внутренняя ошибка сервиса.");
                })
                .timeout(Duration.ofSeconds(30)) // Таймаут 30 секунд
                .doOnSubscribe(subscription ->
                        log.debug("Отправка запроса к DeepSeek: {}", userMessage))
                .doOnSuccess(response ->
                        log.debug("Запрос к DeepSeek выполнен успешно"))
                .doOnError(error ->
                        log.error("Запрос к DeepSeek завершился ошибкой", error));
    }
}

