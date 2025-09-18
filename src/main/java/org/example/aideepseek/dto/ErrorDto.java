package org.example.aideepseek.dto;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorDto {
    private String error;
    private List<ObjectError> listError;

    public ErrorDto() {
    }

    public ErrorDto(String error) {
        this.error = error;
    }

    public ErrorDto(List<ObjectError> listError) {
        this.listError = listError;
    }

    public ErrorDto(String error, List<ObjectError> listError) {
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
