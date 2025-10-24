package org.example.aideepseek.annotation.swagger.controller.subscription;

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
@Operation(summary = "Начать покупку", description = "Сохраняет в кэш информацию о том, что пользователь начал покупку какой-либо подписки/попыток")
@ApiResponse(responseCode = "200", description = "Успешно начали покупку")
@ApiResponse(
        responseCode = "400",
        description = "Валидация не прошла",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
)
@ApiResponse(
        responseCode = "403",
        description = "Необходимо авторизоваться или обновить токен"
)
public @interface SubscriptionControllerAnnotationMethodStartSubscription {
}
