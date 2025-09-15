package org.example.aideepseek.database.service;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseOfSubscription {
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel);
}
