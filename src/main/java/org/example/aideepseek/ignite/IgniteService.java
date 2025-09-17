package org.example.aideepseek.ignite;


import org.apache.ignite.IgniteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IgniteService {

    private Logger log = LoggerFactory.getLogger(IgniteConfig.class);

    @Autowired
    @Qualifier("TaskCache")
    private IgniteCache<String, String> cacheTask;
    @Autowired
    @Qualifier("IpCache")
    private IgniteCache<String, List<String>> cacheIp;

    public void cacheTask(String key, String value) {
        log.debug("put ignite cache :" + cacheTask.getName() + "  key: " + key + "  value: " + value);
        cacheTask.put(key, value);

    }
    public String getTask(String key) {
        var value = cacheTask.get(key);
        log.debug("get ignite cache :" + cacheTask.getName() + "  key: " + key + "  value: " + value);
        return value;
    }


    public int cacheIpGetAndPutElseNewAddress(String username, String ip) {
        log.debug("Setting list ip " + username);
        log.debug("get ignite cache :" + cacheIp.getName() + "  key: " + username);
        List<String> cacheUserIp = Optional.ofNullable(cacheIp.get(username))
                .orElseGet(ArrayList::new);
        if (!cacheUserIp.contains(ip)) {
            log.debug("This new ip address user");
            cacheUserIp.add(ip);
            log.debug("Put key-value");
            cacheIp.put(username, cacheUserIp);
        }
        int size = cacheUserIp.size();
        log.debug("Count users ip address " + size);
        return size;
    }
}
