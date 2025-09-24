package org.example.aideepseek.ignite.repository.subscription;

import org.example.aideepseek.dto.SubscriptionInfoStartDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CacheSubscriptionRepo {
    public SubscriptionInfoStartDto getSubscriptionInfo(UUID id);
    public void setSubscriptionInfo(UUID id, SubscriptionInfoStartDto subscriptionInfo);
    public void removeSubscriptionInfo(UUID id);
}
