package org.example.aideepseek.ignite.service.subscription;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RemoveSubscriptionInfo {
    public void removeSubscriptionInfo(UUID id);
}
