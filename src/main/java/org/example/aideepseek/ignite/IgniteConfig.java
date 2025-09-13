package org.example.aideepseek.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

    private Logger log = LoggerFactory.getLogger(IgniteConfig.class);

    @Value("${ignite.cache.name}")
    private String cacheName;
    @Value("${ignite.memory.request}")
    private long memoryRequest;
    @Value("${ignite.memory.limit}")
    private long memoryLimit;
    @Value("${ignite.max.count.task}")
    private int maxCountTask;

    @Bean
    public Ignite igniteStart() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("IgniteNode");

        DataStorageConfiguration dsCfg = new DataStorageConfiguration();

        DataRegionConfiguration regionCfg = new DataRegionConfiguration();
        regionCfg.setName("TaskCache_region");
        regionCfg.setInitialSize(memoryLimit * 1024 * 1024);
        regionCfg.setMaxSize(memoryRequest * 1024 * 1024);
        regionCfg.setPersistenceEnabled(false);
        regionCfg.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);

        dsCfg.setDefaultDataRegionConfiguration(regionCfg);
        cfg.setDataStorageConfiguration(dsCfg);

        cfg.setPeerClassLoadingEnabled(false);
        cfg.setMetricsLogFrequency(0);
        cfg.setConnectorConfiguration(null);

        cfg.setSystemThreadPoolSize(2);
        cfg.setPublicThreadPoolSize(2);
        cfg.setManagementThreadPoolSize(1);

        Ignite ignite = Ignition.start(cfg);
        log.info("Ignite started with 256MB memory limit and LRU eviction");
        log.info("Node ID: {}", ignite.cluster().localNode().id());
        return ignite;
    }

    @Bean
    public IgniteCache<String, String> igniteCacheTask(Ignite ignite) {
        var cacheCfg = new CacheConfiguration<String, String>(cacheName);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);

        cacheCfg.setEvictionPolicy(new LruEvictionPolicy(maxCountTask));
        cacheCfg.setNearConfiguration(null);

        IgniteCache<String, String> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheName + " ready with up to " + maxCountTask + " entries");
        return cache;
    }

}

