package org.example.aideepseek.ignite.service.account_conformation;

import org.example.aideepseek.dto.SignupDTO;
import org.springframework.stereotype.Service;

@Service
public interface SetCacheAccountConformation {
    public void setCacheAccountConformation(int code, SignupDTO signupDTO);
}
