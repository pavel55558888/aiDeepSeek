package org.example.aideepseek.annotation.swagger.controller.ucassa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Возвращает прикольную html страницу")
@ApiResponse(responseCode = "200", description = "Успешный ответ")
@ApiResponse(responseCode = "500", description = "Файл не найден")
public @interface UCassaSuccessController {
}
