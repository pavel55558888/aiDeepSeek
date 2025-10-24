package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class SignupDTO {
    @Schema(description = "Адрес электронной почты пользователя", example = "user@example.com")
    @Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}",message = "Введите корректный email")
    private String email;

    @Schema(description = "Пароль пользователя", example = "securePassword123")
    @Length(min=5,max=255,message = "Длинна пароля должна быть не менее 5 и не более 255")
    @NotBlank
    private String password;

    public SignupDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignupDTO() {
    }

    public @Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}", message = "Введите корректный email") String getEmail() {
        return email;
    }

    public void setEmail(@Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}", message = "Введите корректный email") String email) {
        this.email = email;
    }

    public @Length(min = 5, max = 255, message = "Длинна пароля должна быть не менее 5 и не более 255") @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@Length(min = 5, max = 255, message = "Длинна пароля должна быть не менее 5 и не более 255") @NotBlank String password) {
        this.password = password;
    }
}
