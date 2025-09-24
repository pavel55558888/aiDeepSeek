package org.example.aideepseek.ignite.service.ip.impl;

import org.example.aideepseek.ignite.repository.ip.CacheIpRepo;
import org.example.aideepseek.ignite.service.ip.CacheIpGetAndPutElseNewAddress;
import org.example.aideepseek.ignite.service.ip.RemoveCacheIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheIpImpl implements RemoveCacheIp, CacheIpGetAndPutElseNewAddress {
    @Autowired
    private CacheIpRepo cacheIpRepo;

    @Override
    public void removeCacheIp(String username) {
        cacheIpRepo.removeCacheIp(username);
    }

    @Override
    public int cacheIpGetAndPutElseNewAddress(String username, String ip) {
        return cacheIpRepo.cacheIpGetAndPutElseNewAddress(username, ip);
    }
}
