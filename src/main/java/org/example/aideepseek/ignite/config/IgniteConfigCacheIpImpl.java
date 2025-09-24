package org.example.aideepseek.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class IgniteConfigCacheIpImpl {

    private static final Logger log = LoggerFactory.getLogger(IgniteConfigCacheIpImpl.class);

    @Value("${ignite.max.count.ip}")
    private int maxCountIp;
    @Value("${ignite.max.hours.ip}")
    private int maxHoursIp;
    @Value("${ignite.cache.name.ip}")
    private String cacheNameIp;

    @Bean("IpCache")
    public IgniteCache<String, List<String>> igniteCacheIp(Ignite ignite) {

        var cacheCfg = new CacheConfiguration<String, List<String>>(cacheNameIp);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);

        cacheCfg.setEvictionPolicy(new LruEvictionPolicy(maxCountIp));
        cacheCfg.setNearConfiguration(null);

        cacheCfg.setExpiryPolicyFactory(
                FactoryBuilder.factoryOf(new CreatedExpiryPolicy(
                        new Duration(TimeUnit.HOURS, maxHoursIp)
                ))
        );


        IgniteCache<String, List<String>> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheNameIp + " ready with up to " + maxCountIp + " entries");
        log.info("Max lifetime hours " + maxHoursIp);
        return cache;
    }
}
