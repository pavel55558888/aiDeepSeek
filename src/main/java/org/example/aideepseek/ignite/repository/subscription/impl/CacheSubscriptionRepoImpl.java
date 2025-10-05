package org.example.aideepseek.ignite.repository.subscription.impl;

import org.apache.ignite.IgniteCache;
import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.example.aideepseek.ignite.repository.subscription.CacheSubscriptionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CacheSubscriptionRepoImpl implements CacheSubscriptionRepo {
    private static final Logger log = LoggerFactory.getLogger(CacheSubscriptionRepoImpl.class);

    @Autowired
    @Qualifier("SubscriptionStartInfo")
    private IgniteCache<UUID, SubscriptionInfoStartDTO> cacheSubscriptionInfo;

    @Override
    public SubscriptionInfoStartDTO getSubscriptionInfo(UUID id) {
        log.debug("get ignite cache :" + cacheSubscriptionInfo.getName() + "  key: " + id);
        return cacheSubscriptionInfo.get(id);
    }

    @Override
    public void setSubscriptionInfo(UUID id, SubscriptionInfoStartDTO subscriptionInfo) {
        log.debug("set ignite cache :" + cacheSubscriptionInfo.getName() + "  key: " + id + "  value: " + subscriptionInfo);
        cacheSubscriptionInfo.put(id, subscriptionInfo);
    }

    @Override
    public void removeSubscriptionInfo(UUID id) {
        log.debug("remove ignite cache :" + cacheSubscriptionInfo.getName() + "  key: " + id);
        cacheSubscriptionInfo.remove(id);
    }
}
