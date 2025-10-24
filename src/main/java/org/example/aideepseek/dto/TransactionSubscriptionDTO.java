package org.example.aideepseek.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;

public class TransactionSubscriptionDTO {
    @Schema(description = "Уникальный идентификатор заказа", example = "1001")
    private long id;

    @Schema(description = "Информация о пользователе, оформившем заказ")
    private UserDTO user;

    @Schema(description = "Общая стоимость заказа", example = "299.99")
    private double price;

    @Schema(description = "Время создания заказа в формате ISO 8601", example = "2025-10-23T15:30:45Z")
    private Timestamp timestamp;

    public TransactionSubscriptionDTO() {
    }

    public TransactionSubscriptionDTO(UserDTO user, double price, Timestamp timestamp) {
        this.price = price;
        this.user = user;
        this.timestamp = timestamp;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}