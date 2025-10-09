package org.example.aideepseek.ignite.repository.account_conformation;

import org.example.aideepseek.dto.SignupDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountConformationCacheRepo {
    public void setCacheAccountConformation(int code, SignupDTO signupDTO);
    public SignupDTO getCacheAccountConformation(int code);
    public void deleteCacheAccountConformation(int code);
}
