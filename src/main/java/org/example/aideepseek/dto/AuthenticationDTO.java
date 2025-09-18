package org.example.aideepseek.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

public class AuthenticationDTO {
    @Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}",message = "Введите корректный email")
    private String email;
    @Length(min=5,max=255,message = "Длинна пароля должна быть не менее 5 и не более 255")
    @NotBlank
    private String password;

    public AuthenticationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationDTO() {
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
