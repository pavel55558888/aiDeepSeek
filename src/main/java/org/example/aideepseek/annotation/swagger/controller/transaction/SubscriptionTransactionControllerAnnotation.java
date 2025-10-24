package org.example.aideepseek.annotation.swagger.controller.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.aideepseek.dto.ConfigUCassaDTO;
import org.example.aideepseek.dto.TransactionSubscriptionDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Получить список всех транзакции", description = "Все произведенные оплаты пользователя вернутся, или пустой список")
@ApiResponse(
        responseCode = "200",
        description = "Успешный ответ",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionSubscriptionDTO.class))
)
@ApiResponse(
        responseCode = "403",
        description = "Необходимо авторизоваться или обновить токен"
)
public @interface SubscriptionTransactionControllerAnnotation {
}
