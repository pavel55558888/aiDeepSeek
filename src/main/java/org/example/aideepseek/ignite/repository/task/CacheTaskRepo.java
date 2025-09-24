package org.example.aideepseek.ignite.repository.task;

import org.springframework.stereotype.Repository;

@Repository
public interface CacheTaskRepo {
    public void setCacheTask(String key, String value);
    public String getTask(String key);

}
