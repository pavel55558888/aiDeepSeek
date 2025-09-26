package org.example.aideepseek.database.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "http_manual")
public class HttpManualModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    private Timestamp timestamp;
    private String manual;

    public HttpManualModel() {
    }

    public HttpManualModel(String url, Timestamp timestamp, String manual) {
        this.url = url;
        this.timestamp = timestamp;
        this.manual = manual;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }
}
