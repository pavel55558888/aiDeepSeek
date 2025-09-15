package org.example.aideepseek.database.service;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.springframework.stereotype.Service;

@Service
public interface SetSubscription {
    public void setSubscription(SubscriptionModel subscription);
}
