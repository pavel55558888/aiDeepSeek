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

@Configuration
public class IgniteConfigCacheTaskImpl {

    private static final Logger log = LoggerFactory.getLogger(IgniteConfigCacheTaskImpl.class);

    @Value("${ignite.cache.name.task}")
    private String cacheNameTask;
    @Value("${ignite.max.count.task}")
    private int maxCountTask;

    @Bean("TaskCache")
    public IgniteCache<String, String> igniteCacheTask(Ignite ignite) {
        var cacheCfg = new CacheConfiguration<String, String>(cacheNameTask);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);

        cacheCfg.setEvictionPolicy(new LruEvictionPolicy(maxCountTask));
        cacheCfg.setNearConfiguration(null);

        IgniteCache<String, String> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheNameTask + " ready with up to " + maxCountTask + " entries");
        return cache;
    }
}
