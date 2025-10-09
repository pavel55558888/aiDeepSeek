package org.example.aideepseek.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.example.aideepseek.dto.SignupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class IgniteConfigCacheAccountConformation {
    private static final Logger log = LoggerFactory.getLogger(IgniteConfigCacheAccountConformation.class);

    @Value("${ignite.max.min.account.conformation}")
    private int maxMinAccountConformation;
    @Value("${ignite.cache.name.account.conformation}")
    private String cacheNameAccountConformation;

    @Bean("AccountConformationCache")
    public IgniteCache<Integer, SignupDTO> igniteCacheIp(Ignite ignite) {

        var cacheCfg = new CacheConfiguration<Integer, SignupDTO>(cacheNameAccountConformation);
        cacheCfg.setBackups(1);
        cacheCfg.setOnheapCacheEnabled(true);
        cacheCfg.setNearConfiguration(null);

        cacheCfg.setExpiryPolicyFactory(
                FactoryBuilder.factoryOf(new CreatedExpiryPolicy(
                        new Duration(TimeUnit.MINUTES, maxMinAccountConformation)
                ))
        );


        IgniteCache<Integer, SignupDTO> cache = ignite.getOrCreateCache(cacheCfg);
        log.info("Cache " + cacheNameAccountConformation + " ready");
        log.info("Max lifetime hours " + maxMinAccountConformation);
        return cache;
    }
}
