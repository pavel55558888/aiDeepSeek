package org.example.aideepseek.database.service.subscription;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseOfSubscription {
    public void purchaseOfSubscription(TransactionSubscriptionModel transactionSubscriptionModel, boolean subscription, int attempt);
}
