package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserDTO {
    @Schema(description = "Уникальный идентификатор пользователя", example = "12345")
    private Long id;

    @Schema(description = "Адрес электронной почты пользователя", example = "user@example.com")
    private String email;

    public UserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
