package org.example.aideepseek.database.repo.subscription;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Subscription {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SubscriptionModel getSubscriptionByEmail(String email);
    @Transactional
    public void setSubscription(SubscriptionModel subscription);
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateSubscription(SubscriptionModel subscription);
    @Transactional
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel, boolean subscription, int attempt);
}
