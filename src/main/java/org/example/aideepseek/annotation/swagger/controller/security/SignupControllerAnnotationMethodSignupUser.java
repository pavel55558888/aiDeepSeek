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
@Operation(summary = "Начать регистрацию", description = "Отправляет на почту код подтвверждения и сохраняет такого пользователя в кэш")
@ApiResponse(responseCode = "200", description = "Успешно начали регистрацию")
@ApiResponse(
        responseCode = "400",
        description = "Валидация не прошла или пользователь уже сущесттвует",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
)
@ApiResponse(responseCode = "504", description = "SMTP не отправил сообщение :(")
public @interface SignupControllerAnnotationMethodSignupUser {
}
