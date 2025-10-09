package org.example.aideepseek.controller.security;

import jakarta.validation.Valid;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.ignite.service.account_conformation.SetCacheAccountConformation;
import org.example.aideepseek.mail.EmailService;
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

import java.util.Random;

@RestController
@RequestMapping("/api/v1")
public class SignupController {

    @Autowired
    private AuthService authService;
    @Autowired
    private SetCacheAccountConformation setCacheAccountConformation;
    @Autowired
    private EmailService emailService;

    private static final Random random = new Random();

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

        int code = random.nextInt(10000,99999);

        emailService.sendEmail(
                signupDTO.getEmail(),
                "Регистрация аккаунта MindAbyss AI",
                "Ваш код подтверждения: " + code +
                "\nНеобходимо подтвердить вашу почту в течение 10 минут");
        setCacheAccountConformation.setCacheAccountConformation(code, signupDTO);

        return ResponseEntity.ok().build();
    }

}
