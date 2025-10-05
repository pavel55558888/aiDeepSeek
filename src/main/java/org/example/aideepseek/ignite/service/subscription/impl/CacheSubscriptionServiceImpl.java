package org.example.aideepseek.ignite.service.subscription.impl;

import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.example.aideepseek.ignite.repository.subscription.CacheSubscriptionRepo;
import org.example.aideepseek.ignite.service.subscription.GetSubscriptionInfo;
import org.example.aideepseek.ignite.service.subscription.RemoveSubscriptionInfo;
import org.example.aideepseek.ignite.service.subscription.SetSubscriptionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CacheSubscriptionServiceImpl implements GetSubscriptionInfo, SetSubscriptionInfo, RemoveSubscriptionInfo {
    @Autowired
    private CacheSubscriptionRepo cacheSubscriptionRepo;

    @Override
    public SubscriptionInfoStartDTO getSubscriptionInfo(UUID id) {
        return cacheSubscriptionRepo.getSubscriptionInfo(id);
    }

    @Override
    public void removeSubscriptionInfo(UUID id) {
        cacheSubscriptionRepo.removeSubscriptionInfo(id);
    }

    @Override
    public void setSubscriptionInfo(UUID id, SubscriptionInfoStartDTO subscriptionInfo) {
        cacheSubscriptionRepo.setSubscriptionInfo(id, subscriptionInfo);
    }
}
