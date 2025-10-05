package org.example.aideepseek.ignite.repository.subscription;

import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CacheSubscriptionRepo {
    public SubscriptionInfoStartDTO getSubscriptionInfo(UUID id);
    public void setSubscriptionInfo(UUID id, SubscriptionInfoStartDTO subscriptionInfo);
    public void removeSubscriptionInfo(UUID id);
}
