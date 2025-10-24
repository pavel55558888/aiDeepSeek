package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationResponse(
        @Schema(description = "JWT-токен для аутентификации", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String jwtToken
) {
}
