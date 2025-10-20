package org.example.aideepseek.controller.deepseek;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.aideepseek.annotation.ApiResponseUnAuth;
import org.example.aideepseek.controller.deepseek.service.DeepSeekControllerService;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.prompt.PromptDeepSeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/v1")
@Tag(name = "Походы в DeepSeek", description = "Обрабатываем запрос, формируем вопрос, отправляем в дипсик и получаем верный ответ")
public class DeepSeekController {
    @Value("${domain.white.list.is}")
    private boolean isWhiteList;
    @Value("${domain.white.list}")
    private List<String> whiteList;

    @Autowired
    private DeepSeekControllerService deepSeekControllerService;

    private static final Logger log = LoggerFactory.getLogger(DeepSeekController.class);

    @Operation(summary = "Запрос в формате html/text", description = "Принимает html, в котором вопрос/ответ и возвращает верный ответ, " +
            "или можно просто текст отправить. Для них разный формат хедеров. В случае html - format/html, текст - format/question. " +
            "Тажке необходимо отправлять домен, с сайта, которого взят html - domainHtml/ya.ru")
    @ApiResponse(responseCode = "200", description = "Правильный ответ успешно отправлен")
    @ApiResponse(responseCode = "400", description = "Неизвестный формат - хедер \"format\" или сайт не находится в белом списке")
    @ApiResponse(responseCode = "500", description = "Не удалось распарсить страницу или ошибки от api DeepSeek")
    @ApiResponseUnAuth
    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody String userMessage,
            @RequestHeader(value = "format") String format,
            @RequestHeader(value = "domainHtml") String domain) {

        if (isWhiteList && !whiteList.contains(domain)) {
            return ResponseEntity.badRequest()
                    .body("This site is not whitelisted");
        }

        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest().body("User message is blank");
        }

        try {

            if ("html".equals(format)) {
                log.debug("html");
                return deepSeekControllerService.handleHtmlFormat(userMessage, domain);
            } else if ("question".equals(format)) {
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