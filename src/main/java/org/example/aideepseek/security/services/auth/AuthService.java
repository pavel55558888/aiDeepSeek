package org.example.aideepseek.security.services.auth;


import org.example.aideepseek.security.dto.SignupDTO;
import org.example.aideepseek.security.dto.UserDTO;
import org.example.aideepseek.security.entities.User;

public interface AuthService {
    UserDTO createUser(SignupDTO signupDTO);
    User getUserByEmail(String email);
}
