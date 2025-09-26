package org.example.aideepseek.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class SubscriptionInfoStartDto implements Serializable {
    @NotNull(message = "ID подписки не может быть null")
    private UUID id;

    @NotNull(message = "Сумма подписки не может быть null")
    @Positive(message = "Сумма подписки должна быть положительной")
    private double value;

    @NotNull(message = "Временная метка не может быть null")
    private Instant created_at;

    @NotBlank(message = "Тип не может быть пустым")
    private String type;

    private String username;

    public SubscriptionInfoStartDto() {
    }

    public SubscriptionInfoStartDto(UUID id, double value, Instant created_at, String type) {
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
