package org.example.aideepseek.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfigGlobal {

    private static final Logger log = LoggerFactory.getLogger(IgniteConfigGlobal.class);

    @Value("${ignite.memory.request}")
    private long memoryRequest;
    @Value("${ignite.memory.limit}")
    private long memoryLimit;
    @Value("${ignite.message.queue.limit}")
    private int messageQueueLimit;
    @Value("${ignite.work.directory}")
    private String workDirectory;

    @Bean
    public Ignite igniteInstance(IgniteConfiguration cfg) {
        Ignite ignite = Ignition.start(cfg);

        log.info("Ignite started with 256MB memory limit and LRU eviction");
        log.info("Node ID: {}", ignite.cluster().localNode().id());
        log.info("Memory limit: " + memoryLimit + ", Memory request: " + memoryRequest);
        log.info("Message queue fast limit: " + messageQueueLimit + ", Message queue slow limit: " + messageQueueLimit/2);

        return ignite;
    }

    @Bean
    public IgniteConfiguration igniteConfiguration(DataStorageConfiguration dsCfg, TcpCommunicationSpi commSpi) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("IgniteNode");

        cfg.setDataStorageConfiguration(dsCfg);

        cfg.setPeerClassLoadingEnabled(false);
        cfg.setMetricsLogFrequency(0);
        cfg.setConnectorConfiguration(null);

        cfg.setSystemThreadPoolSize(2);
        cfg.setPublicThreadPoolSize(2);
        cfg.setManagementThreadPoolSize(1);

        cfg.setWorkDirectory(workDirectory);

        cfg.setCommunicationSpi(commSpi);
        return cfg;
    }

    @Bean
    public TcpCommunicationSpi tcpCommunicationSpi() {
        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();
        commSpi.setMessageQueueLimit(messageQueueLimit);
        commSpi.setSlowClientQueueLimit(messageQueueLimit / 2);
        return commSpi;
    }

    @Bean
    public DataStorageConfiguration dataStorageConfiguration(DataRegionConfiguration regionCfg) {
        DataStorageConfiguration dsCfg = new DataStorageConfiguration();
        dsCfg.setDefaultDataRegionConfiguration(regionCfg);

        return dsCfg;
    }

    @Bean
    public DataRegionConfiguration dataRegionConfiguration() {
        DataRegionConfiguration regionCfg = new DataRegionConfiguration();
        regionCfg.setName("Default_Region");
        regionCfg.setInitialSize(memoryRequest * 1024 * 1024);
        regionCfg.setMaxSize(memoryLimit * 1024 * 1024);
        regionCfg.setPersistenceEnabled(false);
        regionCfg.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);
        return regionCfg;
    }

}

