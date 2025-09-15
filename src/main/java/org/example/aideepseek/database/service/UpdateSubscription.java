package org.example.aideepseek.database.service;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateSubscription {
    public void updateSubscription(SubscriptionModel subscription);
}
