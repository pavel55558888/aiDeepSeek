package org.example.aideepseek.database.repo.transaction.impl;


import jakarta.persistence.EntityManager;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.repo.transaction.TransactionSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionSubscriptionImpl implements TransactionSubscription {
    @Autowired
    EntityManager entityManager;
    @Override
    public void setTransaction(TransactionSubscriptionModel transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public List<TransactionSubscriptionModel> getTransaction(String email) {
        return entityManager.createQuery("SELECT t FROM TransactionSubscriptionModel t JOIN t.user u WHERE u.email = :email", TransactionSubscriptionModel.class)
                .setParameter("email", email)
                .getResultList();
    }
}
