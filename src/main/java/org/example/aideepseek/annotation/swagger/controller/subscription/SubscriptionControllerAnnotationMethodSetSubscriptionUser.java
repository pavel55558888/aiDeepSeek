package org.example.aideepseek.annotation.swagger.controller.subscription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Закончить покупку", description = "Юкасса отправляет уведомление после успешной оплаты, в данном случае обрабатываем только успешные")
@ApiResponse(responseCode = "200", description = "Подписка подключилась/попытки зачислены")
@ApiResponse(
        responseCode = "404",
        description = "Не найден данный кэш, либо прошло более 10 минуты, либо такой пользователь вообще не начинал покупку"
)
@ApiResponse(
        responseCode = "400",
        description = "Неверный формат подписки или кол-во попыток"
)
public @interface SubscriptionControllerAnnotationMethodSetSubscriptionUser {
}
