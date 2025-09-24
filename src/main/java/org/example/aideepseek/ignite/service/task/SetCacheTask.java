package org.example.aideepseek.ignite.service.task;

import org.springframework.stereotype.Service;

@Service
public interface SetCacheTask {
    public void setCacheTask(String key, String value);
}
