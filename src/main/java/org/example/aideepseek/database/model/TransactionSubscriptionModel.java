package org.example.aideepseek.database.model;

import jakarta.persistence.*;
import org.example.aideepseek.security.entities.User;

import java.sql.Timestamp;

@Entity
@Table(name = "transaction_subsctiprion")
public class TransactionSubscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    private double price;
    private Timestamp timestamp;

    public TransactionSubscriptionModel() {
    }

    public TransactionSubscriptionModel(User user, double price) {
        this.price = price;
        this.user = user;
        this.timestamp = new Timestamp(System.currentTimeMillis());
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
