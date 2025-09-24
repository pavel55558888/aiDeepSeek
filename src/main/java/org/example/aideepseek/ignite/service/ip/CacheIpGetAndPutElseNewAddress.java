package org.example.aideepseek.ignite.service.ip;

import org.springframework.stereotype.Service;

@Service
public interface CacheIpGetAndPutElseNewAddress {
    public int cacheIpGetAndPutElseNewAddress(String username, String ip);
}
