package org.example.aideepseek.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.example.aideepseek.dto.SubscriptionInfoStartDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class IgniteConfig {

    private Logger log = LoggerFactory.getLogger(IgniteConfig.class);

    @Value("${ignite.cache.name.task}")
    private String cacheNameTask;
    @Value("${ignite.cache.name.ip}")
    private String cacheNameIp;
    @Value("${ignite.cache.name.subscription}")
    private String cacheNameSubscription;
    @Value("${ignite.memory.request}")
    private long memoryRequest;
    @Value("${ignite.memory.limit}")
    private long memoryLimit;
    @Value("${ignite.max.count.task}")
    private int maxCountTask;
    @Value("${ignite.max.count.ip}")
    private int maxCountIp;
    @Value("${ignite.max.hours.ip}")
    private int maxHoursIp;
    @Value("${ignite.max.count.subscription}")
    private int maxCountSubscription;
    @Value("${ignite.max.min.subscription}")
    private int maxMinSubscription;
    @Value("${ignite.message.queue.limit}")
    private int messageQueueLimit;
    @Value("${ignite.work.directory}")
    private String workDirectory;

    @Bean
    public Ignite igniteStart() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("IgniteNode");

        DataStorageConfiguration dsCfg = new DataStorageConfiguration();

        DataRegionConfiguration regionCfg = new DataRegionConfiguration();
        regionCfg.setName("TaskCache_region");
        regionCfg.setInitialSize(memoryRequest * 1024 * 1024);
        regionCfg.setMaxSize(memoryLimit * 1024 * 1024);
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

        cfg.setWorkDirectory(workDirectory);

        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();
        commSpi.setMessageQueueLimit(messageQueueLimit);
        commSpi.setSlowClientQueueLimit(messageQueueLimit / 2);
        cfg.setCommunicationSpi(commSpi);

        Ignite ignite = Ignition.start(cfg);
        log.info("Ignite started with 256MB memory limit and LRU eviction");
        log.info("Node ID: {}", ignite.cluster().localNode().id());
        log.info("Memory limit: " + memoryLimit + ", Memory request: " + memoryRequest);
        log.info("Message queue fast limit: " + messageQueueLimit + ", Message queue slow limit: " + messageQueueLimit/2);
        return ignite;
    }

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

    @Bean("SubscriptionStartInfo")
    public IgniteCache<UUID, SubscriptionInfoStartDto> igniteSubscriptionStartInfo(Ignite ignite) {

        var cacheCfg = new CacheConfiguration<UUID, SubscriptionInfoStartDto>(cacheNameSubscription);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);

        cacheCfg.setEvictionPolicy(new LruEvictionPolicy(maxCountSubscription));
        cacheCfg.setNearConfiguration(null);

        cacheCfg.setExpiryPolicyFactory(
                FactoryBuilder.factoryOf(new CreatedExpiryPolicy(
                        new Duration(TimeUnit.MINUTES, maxMinSubscription)
                ))
        );


        IgniteCache<UUID, SubscriptionInfoStartDto> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheNameSubscription + " ready with up to " + maxCountSubscription + " entries");
        log.info("Max lifetime min " + maxMinSubscription);
        return cache;
    }

}

