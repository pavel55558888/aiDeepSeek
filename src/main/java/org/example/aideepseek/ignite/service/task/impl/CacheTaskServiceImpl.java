package org.example.aideepseek.ignite.service.task.impl;

import org.example.aideepseek.ignite.repository.task.CacheTaskRepo;
import org.example.aideepseek.ignite.service.task.GetTask;
import org.example.aideepseek.ignite.service.task.SetCacheTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheTaskServiceImpl implements GetTask, SetCacheTask {

    @Autowired
    private CacheTaskRepo cacheTaskRepo;

    @Override
    public String getTask(String key) {
        return cacheTaskRepo.getTask(key);
    }

    @Override
    public void setCacheTask(String key, String value) {
        cacheTaskRepo.setCacheTask(key, value);
    }
}
