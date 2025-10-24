package org.example.aideepseek.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class ConfigUCassaDTO {
    @Schema(description = "Уникальный идентификатор тарифа", example = "101")
    private long id;

    @Schema(description = "Идентификатор магазина, к которому привязан тариф", example = "501")
    private long shopId;

    @Schema(description = "Уникальный ключ магазина", example = "test_js9I0tQMwCpPCc4xxDbrC1z6dea349mWcYhrJ6OmCvc")
    private String key;

    @Schema(description = "Описание тарифа", example = "Премиум-подписка на 1 месяц")
    private String description;

    @Schema(description = "Стоимость тарифа в валюте", example = "299.99")
    private double value;

    @Schema(description = "Тип тарифа)", example = "subscription")
    private String type;

    public ConfigUCassaDTO() {
    }

    public ConfigUCassaDTO(long shopId, String key, String description, double value, String type) {
        this.shopId = shopId;
        this.key = key;
        this.description = description;
        this.value = value;
        this.type = type;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
