package org.example.aideepseek.controller.security;

import jakarta.validation.Valid;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.subscription.SetSubscription;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.dto.UserDTO;
import org.example.aideepseek.security.entities.User;
import org.example.aideepseek.security.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1")
public class SignupController {

    @Autowired
    private AuthService authService;
    @Autowired
    private SetSubscription setSubscription;
    @Value("${free.attempt.user}")
    private int freeAttempt;

    private static final ErrorDTO errorDto = new ErrorDTO();

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            errorDto.setListError(bindingResult.getAllErrors());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        User userIsNew = authService.getUserByEmail(signupDTO.getEmail());
        if (userIsNew != null){
            return new ResponseEntity<>("Такой пользователь уже существует", HttpStatus.BAD_REQUEST);
        }

        UserDTO createdUser = authService.createUser(signupDTO);
        if (createdUser == null){
            return new ResponseEntity<>("Не получилось создать пользователя", HttpStatus.BAD_REQUEST);
        }
            setSubscription
                    .setSubscription(
                            new SubscriptionModel(
                                    authService.getUserByEmail(signupDTO.getEmail()),
                                    new Timestamp(System.currentTimeMillis()),
                                    freeAttempt, Status.INACTIVE)
                    );
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
