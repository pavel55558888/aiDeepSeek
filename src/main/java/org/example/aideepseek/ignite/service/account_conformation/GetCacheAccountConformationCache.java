package org.example.aideepseek.ignite.service.account_conformation;

import org.example.aideepseek.dto.SignupDTO;
import org.springframework.stereotype.Service;

@Service
public interface GetCacheAccountConformationCache {
    public SignupDTO getCacheAccountConformation(int code);
}
