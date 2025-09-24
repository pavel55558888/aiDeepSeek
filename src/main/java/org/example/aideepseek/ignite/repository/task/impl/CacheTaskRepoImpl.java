package org.example.aideepseek.ignite.repository.task.impl;

import org.apache.ignite.IgniteCache;
import org.example.aideepseek.ignite.repository.task.CacheTaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CacheTaskRepoImpl implements CacheTaskRepo {
    private static final Logger log = LoggerFactory.getLogger(CacheTaskRepoImpl.class);

    @Autowired
    @Qualifier("TaskCache")
    private IgniteCache<String, String> cacheTask;

    @Override
    public void setCacheTask(String key, String value) {
        log.debug("put ignite cache :" + cacheTask.getName() + "  key: " + key + "  value: " + value);
        cacheTask.put(key, value);

    }

    @Override
    public String getTask(String key) {
        var value = cacheTask.get(key);
        log.debug("get ignite cache :" + cacheTask.getName() + "  key: " + key + "  value: " + value);
        return value;
    }
}
