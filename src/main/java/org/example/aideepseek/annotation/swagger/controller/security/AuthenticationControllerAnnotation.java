package org.example.aideepseek.annotation.swagger.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.aideepseek.dto.ErrorDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Авторизация", description = "Принимает данные пользователя и возвращает jwt токен, который живет n часов")
@ApiResponse(responseCode = "200", description = "Успешно авторизовались, в ответ получили токен")
@ApiResponse(responseCode = "403", description = "Неверные данные")
@ApiResponse(
        responseCode = "400",
        description = "Валидация не прошла",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
)
public @interface AuthenticationControllerAnnotation {
}
