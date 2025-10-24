package org.example.aideepseek.annotation.swagger.controller.deepseek;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Запрос в формате html/text", description = "Принимает html, в котором вопрос/ответ и возвращает верный ответ, " +
        "или можно просто текст отправить. Для них разный формат хедеров. В случае html - format/html, текст - format/question. " +
        "Тажке необходимо отправлять домен, с сайта, которого взят html - domainHtml/ya.ru")
@ApiResponse(responseCode = "200", description = "Правильный ответ успешно отправлен")
@ApiResponse(responseCode = "400", description = "Неизвестный формат - хедер \"format\" или сайт не находится в белом списке")
@ApiResponse(responseCode = "500", description = "Не удалось распарсить страницу или ошибки от api DeepSeek")
@ApiResponse(responseCode = "403", description = "Необходимо авторизоваться или обновить токен")
public @interface DeepSeekControllerAnnotation {
}
