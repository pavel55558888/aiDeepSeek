package org.example.aideepseek.database.repo;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionSubscription {
    @Transactional
    void setTransaction(TransactionSubscriptionModel transaction);
    @Transactional
    List<TransactionSubscriptionModel> getTransaction(String email);
}
