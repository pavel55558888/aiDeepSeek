package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class SubscriptionInfoStartDTO implements Serializable {
    @Schema(description = "Уникальный идентификатор подписки", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    @NotNull(message = "ID подписки не может быть null")
    private UUID id;

    @Schema(description = "Сумма подписки в валюте", example = "99.99")
    @NotNull(message = "Сумма подписки не может быть null")
    @Positive(message = "Сумма подписки должна быть положительной")
    private double value;

    @Schema(description = "Время создания подписки в формате ISO 8601", example = "2025-10-23T10:30:00Z")
    @NotNull(message = "Временная метка не может быть null")
    private Instant created_at;

    @Schema(description = "Тип подписки", example = "PREMIUM")
    @NotBlank(message = "Тип не может быть пустым")
    private String type;

    @Schema(description = "Имя пользователя, оформившего подписку", example = "john_doe@example.com")
    private String username;

    public SubscriptionInfoStartDTO() {
    }

    public SubscriptionInfoStartDTO(UUID id, double value, Instant created_at, String type) {
        this.id = id;
        this.value = value;
        this.created_at = created_at;
        this.type = type;
    }

    @NotNull(message = "Сумма подписки не может быть null")
    @Positive(message = "Сумма подписки должна быть положительной")
    public double getValue() {
        return value;
    }

    public void setValue(@NotNull(message = "Сумма подписки не может быть null") @Positive(message = "Сумма подписки должна быть положительной") double value) {
        this.value = value;
    }

    public @NotNull(message = "ID подписки не может быть null") UUID getId() {
        return id;
    }

    public void setId(@NotNull(message = "ID подписки не может быть null") UUID id) {
        this.id = id;
    }

    public @NotNull(message = "Временная метка не может быть null") Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NotNull(message = "Временная метка не может быть null") Instant created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @NotBlank(message = "Тип не может быть пустым") String getType() {
        return type;
    }

    public void setType(@NotBlank(message = "Тип не может быть пустым") String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubscriptionInfoStartDto{" +
                "id=" + id +
                ", value=" + value +
                ", timestamp=" + created_at +
                ", username='" + username + '\'' +
                '}';
    }
}
