package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public class SubscriptionInfoStopDTO {

    @Schema(description = "Уникальный идентификатор платежа", example = "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8")
    @NotNull(message = "ID платежа не может быть null")
    private UUID id;

    @Schema(description = "Сумма платежа в валюте", example = "49.99")
    @NotNull(message = "Сумма платежа не может быть null")
    @Positive(message = "Сумма платежа должна быть положительной")
    private double value;

    @Schema(description = "Время совершения платежа в формате ISO 8601", example = "2025-10-23T14:30:00Z")
    @NotNull(message = "Временная метка не может быть null")
    private Instant created_at;

    public SubscriptionInfoStopDTO() {
    }

    public SubscriptionInfoStopDTO(UUID id, double value, Instant created_at) {
        this.id = id;
        this.value = value;
        this.created_at = created_at;
    }

    public @NotNull(message = "ID платежа не может быть null") UUID getId() {
        return id;
    }

    public void setId(@NotNull(message = "ID платежа не может быть null") UUID id) {
        this.id = id;
    }

    public @NotNull(message = "Сумма платежа не может быть null")
    @Positive(message = "Сумма платежа должна быть положительной") double getValue() {
        return value;
    }

    public void setValue(@NotNull(message = "Сумма платежа не может быть null")
                         @Positive(message = "Сумма платежа должна быть положительной") double value) {
        this.value = value;
    }

    public @NotNull(message = "Временная метка не может быть null") Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NotNull(message = "Временная метка не может быть null") Instant created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "SubscriptionInfoStopDto{" +
                "id=" + id +
                ", value=" + value +
                ", created_at=" + created_at +
                '}';
    }
}