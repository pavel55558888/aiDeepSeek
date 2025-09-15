package org.example.aideepseek.database.model;

import jakarta.persistence.*;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.security.entities.User;

import java.sql.Timestamp;

@Entity
@Table(name = "subscription")
public class SubscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private Timestamp timestamp;
    @Column(name = "free_attempt")
    private int freeAttempt;
    private Status status;

    public SubscriptionModel() {
    }

    public SubscriptionModel(User user, Timestamp timestamp, int freeAttempt, Status status) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFreeAttempt() {
        return freeAttempt;
    }

    public void setFreeAttempt(int freeAttempt) {
        this.freeAttempt = freeAttempt;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
