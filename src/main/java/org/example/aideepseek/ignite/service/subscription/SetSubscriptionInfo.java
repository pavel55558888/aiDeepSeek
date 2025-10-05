package org.example.aideepseek.ignite.service.subscription;

import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface SetSubscriptionInfo {
    public void setSubscriptionInfo(UUID id, SubscriptionInfoStartDTO subscriptionInfo);
}
