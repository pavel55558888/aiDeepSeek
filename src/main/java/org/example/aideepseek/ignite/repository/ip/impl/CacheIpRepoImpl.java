package org.example.aideepseek.ignite.repository.ip.impl;

import org.apache.ignite.IgniteCache;
import org.example.aideepseek.ignite.repository.ip.CacheIpRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CacheIpRepoImpl implements CacheIpRepo {
    private final static Logger log = LoggerFactory.getLogger(CacheIpRepoImpl.class);

    @Autowired
    @Qualifier("IpCache")
    private IgniteCache<String, List<String>> cacheIp;

    @Override
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

    @Override
    public void removeCacheIp(String username) {
        log.debug("Delete ignite cache :" + cacheIp.getName() + "  key: " + username);
        cacheIp.remove(username);
    }
}
