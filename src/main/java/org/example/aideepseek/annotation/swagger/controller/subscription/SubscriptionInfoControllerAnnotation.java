package org.example.aideepseek.annotation.swagger.controller.subscription;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Получить информацию",
        description = "Вернет объект, который создается при регистрации и хранится в бд, статусы: ACTIVE,BLOCKED,INACTIVE"
)
@ApiResponse(responseCode = "200", description = "Успешный ответ")
@ApiResponse(
        responseCode = "403",
        description = "Необходимо авторизоваться или обновить токен"
)
public @interface SubscriptionInfoControllerAnnotation {
}
