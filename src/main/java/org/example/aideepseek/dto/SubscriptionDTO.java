package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.aideepseek.database.model.enums.Status;

import java.sql.Timestamp;

public class SubscriptionDTO {
    @Schema(description = "Уникальный идентификатор", example = "5001")
    private long id;
    @Schema(description = "Пользователь, к которому привязана подписка")
    private UserDTO user;
    @Schema(description = "Время начала сессии в формате ISO 8601", example = "2025-10-23T16:45:30Z")
    private Timestamp timestamp;
    @Schema(description = "Количество оставшихся бесплатных попыток", example = "3")
    private int freeAttempt;
    @Schema(description = "Текущий статус сессии", example = "ACTIVE")
    private Status status;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(long id, UserDTO user, Timestamp timestamp, int freeAttempt, Status status) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
        this.freeAttempt = freeAttempt;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getFreeAttempt() {
        return freeAttempt;
    }

    public void setFreeAttempt(int freeAttempt) {
        this.freeAttempt = freeAttempt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
