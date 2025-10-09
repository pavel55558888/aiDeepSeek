package org.example.aideepseek.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class IgniteConfigCacheSubscriptionStartInfo {

    private static final Logger log = LoggerFactory.getLogger(IgniteConfigCacheSubscriptionStartInfo.class);

    @Value("${ignite.max.count.subscription}")
    private int maxCountSubscription;
    @Value("${ignite.max.min.subscription}")
    private int maxMinSubscription;
    @Value("${ignite.cache.name.subscription}")
    private String cacheNameSubscription;

    @Bean("SubscriptionStartInfo")
    public IgniteCache<UUID, SubscriptionInfoStartDTO> igniteSubscriptionStartInfo(Ignite ignite) {

        var cacheCfg = new CacheConfiguration<UUID, SubscriptionInfoStartDTO>(cacheNameSubscription);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);

        cacheCfg.setEvictionPolicy(new LruEvictionPolicy(maxCountSubscription));
        cacheCfg.setNearConfiguration(null);

        cacheCfg.setExpiryPolicyFactory(
                FactoryBuilder.factoryOf(new CreatedExpiryPolicy(
                        new Duration(TimeUnit.MINUTES, maxMinSubscription)
                ))
        );


        IgniteCache<UUID, SubscriptionInfoStartDTO> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheNameSubscription + " ready with up to " + maxCountSubscription + " entries");
        log.info("Max lifetime min " + maxMinSubscription);
        return cache;
    }
}
