package org.example.aideepseek.ignite.repository.account_conformation.impl;

import org.apache.ignite.IgniteCache;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.ignite.repository.account_conformation.AccountConformationCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AccountConformationCacheRepoImpl implements AccountConformationCacheRepo {
    @Autowired
    @Qualifier("AccountConformationCache")
    private IgniteCache<Integer, SignupDTO> accountConformationCache;

    @Override
    public void setCacheAccountConformation(int code, SignupDTO signupDTO) {
        accountConformationCache.put(code, signupDTO);
    }

    @Override
    public SignupDTO getCacheAccountConformation(int code) {
        return accountConformationCache.get(code);
    }

    @Override
    public void deleteCacheAccountConformation(int code) {
        accountConformationCache.remove(code);
    }
}
