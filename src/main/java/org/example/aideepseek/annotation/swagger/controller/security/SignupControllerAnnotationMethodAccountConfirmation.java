package org.example.aideepseek.annotation.swagger.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.aideepseek.dto.UserDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Подтверждение почты", description = "Берет из кэша данные пользователя и сохраняет в бд, если отправленный код правильный")
@ApiResponse(
        responseCode = "201",
        description = "Успешная регистрация",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
)
@ApiResponse(responseCode = "408", description = "Пользователь опоздал, n минут выделенные на регистрацию исчерпаны")
@ApiResponse(responseCode = "500", description = "По каком-то причинам не создался пользователь")
public @interface SignupControllerAnnotationMethodAccountConfirmation {
}
