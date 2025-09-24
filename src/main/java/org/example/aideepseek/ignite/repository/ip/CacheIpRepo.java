package org.example.aideepseek.ignite.repository.ip;

import org.springframework.stereotype.Repository;

@Repository
public interface CacheIpRepo {
    public int cacheIpGetAndPutElseNewAddress(String username, String ip);
    public void removeCacheIp(String username);
}
