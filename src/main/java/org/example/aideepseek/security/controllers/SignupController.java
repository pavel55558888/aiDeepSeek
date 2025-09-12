package org.example.aideepseek.security.controllers;

import jakarta.validation.Valid;
import org.example.aideepseek.security.dto.DtoError;
import org.example.aideepseek.security.dto.SignupDTO;
import org.example.aideepseek.security.dto.UserDTO;
import org.example.aideepseek.security.entities.User;
import org.example.aideepseek.security.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class SignupController {

    @Autowired
    private AuthService authService;
    @Autowired
    private DtoError dtoError;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            dtoError.setListError(bindingResult.getAllErrors());
            return new ResponseEntity<>(dtoError, HttpStatus.BAD_REQUEST);
        }
        User userIsNew = authService.getUserByEmail(signupDTO.getEmail());
        if (userIsNew != null){
            return new ResponseEntity<>("Такой пользователь уже существует", HttpStatus.BAD_REQUEST);
        }

        UserDTO createdUser = authService.createUser(signupDTO);
        if (createdUser == null){
            return new ResponseEntity<>("Не получилось создать пользователя", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
