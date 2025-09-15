package org.example.aideepseek.security.dto;

import org.springframework.validation.ObjectError;

import java.util.List;

public class DtoError {
    private String error;
    private List<ObjectError> listError;

    public DtoError() {
    }

    public DtoError(String error) {
        this.error = error;
    }

    public DtoError(List<ObjectError> listError) {
        this.listError = listError;
    }

    public DtoError(String error, List<ObjectError> listError) {
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
