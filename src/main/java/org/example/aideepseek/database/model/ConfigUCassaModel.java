package org.example.aideepseek.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "config_ucassa")
public class ConfigUCassaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long shopId;
    private String key;
    private String description;

    public ConfigUCassaModel() {
    }

    public ConfigUCassaModel(long shopId, String key, String description) {
        this.shopId = shopId;
        this.key = key;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
