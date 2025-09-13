package org.example.aideepseek.ignite;


import org.apache.ignite.IgniteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IgniteService {

    private Logger log = LoggerFactory.getLogger(IgniteConfig.class);

    @Autowired
    private IgniteCache<String, String> cache;

    public void cacheTask(String key, String value) {
        log.debug("put ignite cache :" + cache.getName() + "  key: " + key + "  value: " + value);
        cache.put(key, value);

    }
    public String getTask(String key) {
        var value = cache.get(key);
        log.debug("get ignite cache :" + cache.getName() + "  key: " + key + "  value: " + value);
        return value;
    }
}
