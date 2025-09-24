package org.example.aideepseek.ignite.service.task;

import org.springframework.stereotype.Service;

@Service
public interface GetTask {
    public String getTask(String key);
}
