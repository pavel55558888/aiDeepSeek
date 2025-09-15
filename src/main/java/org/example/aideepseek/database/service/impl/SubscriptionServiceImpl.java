package org.example.aideepseek.database.service.impl;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.repo.Subscription;
import org.example.aideepseek.database.service.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.PurchaseOfSubscription;
import org.example.aideepseek.database.service.SetSubscription;
import org.example.aideepseek.database.service.UpdateSubscription;
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
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel) {
        subscription.purchaseOfSubscription(transactionSubscriptionModel);
    }
}
