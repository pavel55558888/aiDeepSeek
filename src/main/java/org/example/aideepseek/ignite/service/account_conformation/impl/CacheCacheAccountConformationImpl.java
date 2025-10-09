package org.example.aideepseek.ignite.service.account_conformation.impl;

import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.ignite.repository.account_conformation.AccountConformationCacheRepo;
import org.example.aideepseek.ignite.service.account_conformation.DeleteCacheAccountConformation;
import org.example.aideepseek.ignite.service.account_conformation.GetCacheAccountConformationCache;
import org.example.aideepseek.ignite.service.account_conformation.SetCacheAccountConformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheCacheAccountConformationImpl implements GetCacheAccountConformationCache, SetCacheAccountConformation, DeleteCacheAccountConformation {
    @Autowired
    private AccountConformationCacheRepo accountConformationCacheRepo;

    @Override
    public void deleteCacheAccountConformation(int code) {
        accountConformationCacheRepo.deleteCacheAccountConformation(code);
    }

    @Override
    public void setCacheAccountConformation(int code, SignupDTO signupDTO) {
        accountConformationCacheRepo.setCacheAccountConformation(code, signupDTO);
    }

    @Override
    public SignupDTO getCacheAccountConformation(int code) {
        return accountConformationCacheRepo.getCacheAccountConformation(code);
    }
}
