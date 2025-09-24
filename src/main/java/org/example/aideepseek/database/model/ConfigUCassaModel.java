package org.example.aideepseek.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "config_ucassa")
public class ConfigUCassaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long shopId;
    private String key;
    private String description;
    private double value;
    private String type;

    public ConfigUCassaModel() {
    }

    public ConfigUCassaModel(long shopId, String key, String description, double value, String type) {
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
