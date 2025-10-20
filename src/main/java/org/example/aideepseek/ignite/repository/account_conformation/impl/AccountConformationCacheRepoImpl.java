package org.example.aideepseek.ignite.repository.account_conformation.impl;

import org.apache.ignite.IgniteCache;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.ignite.repository.account_conformation.AccountConformationCacheRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AccountConformationCacheRepoImpl implements AccountConformationCacheRepo {
    private static final Logger log = LoggerFactory.getLogger(AccountConformationCacheRepoImpl.class);
    @Autowired
    @Qualifier("AccountConformationCache")
    private IgniteCache<Integer, SignupDTO> accountConformationCache;

    @Override
    public void setCacheAccountConformation(int code, SignupDTO signupDTO) {
        log.debug("setCacheAccountConformation called with code {}", code);
        accountConformationCache.put(code, signupDTO);
    }

    @Override
    public SignupDTO getCacheAccountConformation(int code) {
        log.debug("getCacheAccountConformation called with code {}", code);
        return accountConformationCache.get(code);
    }

    @Override
    public void deleteCacheAccountConformation(int code) {
        log.debug("deleteCacheAccountConformation called with code {}", code);
        accountConformationCache.remove(code);
    }
}
