package org.example.aideepseek.annotation.swagger.controller.ucassa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.aideepseek.dto.ConfigUCassaDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Получить список подписок",
        description = "Возвращает все возможные подписки/попытки, которые можно купить и магазин"
)
@ApiResponse(
        responseCode = "200",
        description = "Успешный ответ",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfigUCassaDTO.class))
)
@ApiResponse(
        responseCode = "403",
        description = "Необходимо авторизоваться или обновить токен"
)
@ApiResponse(responseCode = "500", description = "В бд нет никаких подписок, сервер их не подгрузил, возможно в пропертя не положили их")
public @interface ConfigUcassaControllerAnnotation {
}
