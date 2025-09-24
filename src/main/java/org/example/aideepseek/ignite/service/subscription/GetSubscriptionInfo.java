package org.example.aideepseek.ignite.service.subscription;

import org.example.aideepseek.dto.SubscriptionInfoStartDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface GetSubscriptionInfo {
    public SubscriptionInfoStartDto getSubscriptionInfo(UUID id);
}
