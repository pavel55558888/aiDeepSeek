package org.example.aideepseek.database.repo.impl;

import jakarta.persistence.EntityManager;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.repo.Subscription;
import org.example.aideepseek.database.service.SetTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class SubscriptionImpl implements Subscription {
    @Autowired
    private EntityManager entityManager;

    @Override
    public SubscriptionModel getSubscriptionByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                        SubscriptionModel.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void setSubscription(SubscriptionModel subscription) {
        entityManager.persist(subscription);
    }

    @Override
    public void updateSubscription(SubscriptionModel subscription) {
        entityManager.merge(subscription);
    }

    @Override
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel) {
        entityManager.persist(transactionSubscriptionModel);
        String email = transactionSubscriptionModel.getUser().getEmail();
        SubscriptionModel subscriptionModel = entityManager.createQuery(
                "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                        SubscriptionModel.class)
                .setParameter("email", email)
                .getSingleResult();

        subscriptionModel.setTimestamp(new Timestamp(System.currentTimeMillis()));
        subscriptionModel.setStatus(Status.ACTIVE);
        entityManager.merge(subscriptionModel);
    }
}
