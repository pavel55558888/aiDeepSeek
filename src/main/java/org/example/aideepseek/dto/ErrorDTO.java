package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorDTO {
    @Schema(description = "Обычная строка с ошибкой", example = "Ошибка: бесплатные попытки закончились. Оформите подписку")
    private String error;
    @Schema(description = "Ошибка по валидации")
    private List<ObjectError> listError;

    public ErrorDTO() {
    }

    public ErrorDTO(String error) {
        this.error = error;
    }

    public ErrorDTO(List<ObjectError> listError) {
        this.listError = listError;
    }

    public ErrorDTO(String error, List<ObjectError> listError) {
        this.error = error;
        this.listError = listError;
    }

    public List<ObjectError> getListError() {
        return listError;
    }

    public void setListError(List<ObjectError> listError) {
        this.listError = listError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
