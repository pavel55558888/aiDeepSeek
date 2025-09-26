package org.example.aideepseek.database.service.subscription.impl;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.repo.subscription.Subscription;
import org.example.aideepseek.database.service.subscription.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.subscription.PurchaseOfSubscription;
import org.example.aideepseek.database.service.subscription.SetSubscription;
import org.example.aideepseek.database.service.subscription.UpdateSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements GetSubscriptionByEmail, SetSubscription, UpdateSubscription, PurchaseOfSubscription {
    @Autowired
    private Subscription subscription;
    @Override
    public SubscriptionModel getSubscriptionByEmail(String email) {
        return subscription.getSubscriptionByEmail(email);
    }

    @Override
    public void setSubscription(SubscriptionModel subscriptionModel) {
        subscription.setSubscription(subscriptionModel);
    }

    @Override
    public void updateSubscription(SubscriptionModel subscriptionModel) {
        subscription.updateSubscription(subscriptionModel);
    }

    @Override
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel, boolean subscription, int attempt) {
        this.subscription.purchaseOfSubscription(transactionSubscriptionModel, subscription, attempt);
    }
}
