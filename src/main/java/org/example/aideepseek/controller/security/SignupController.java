package org.example.aideepseek.controller.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.aideepseek.annotation.swagger.controller.security.SignupControllerAnnotationMethodAccountConfirmation;
import org.example.aideepseek.annotation.swagger.controller.security.SignupControllerAnnotationMethodSignupUser;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.subscription.SetSubscription;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.dto.UserDTO;
import org.example.aideepseek.ignite.service.account_conformation.DeleteCacheAccountConformation;
import org.example.aideepseek.ignite.service.account_conformation.GetCacheAccountConformationCache;
import org.example.aideepseek.ignite.service.account_conformation.SetCacheAccountConformation;
import org.example.aideepseek.mail.EmailService;
import org.example.aideepseek.mail.pattern.PatternEmailMessage;
import org.example.aideepseek.security.entities.User;
import org.example.aideepseek.security.services.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Регистрация аккаунта", description = "Проходит в 2 этапа")
public class SignupController {

    @Autowired
    private AuthService authService;
    @Autowired
    private SetCacheAccountConformation setCacheAccountConformation;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SetSubscription setSubscription;
    @Value("${free.attempt.user}")
    private int freeAttempt;
    @Autowired
    private GetCacheAccountConformationCache getCacheAccountConformationCache;
    @Autowired
    private DeleteCacheAccountConformation deleteCacheAccountConformation;

    private static final Random random = new Random();
    private static final ErrorDTO errorDto = new ErrorDTO();
    private static final Logger log = LoggerFactory.getLogger(SignupController.class);


    @SignupControllerAnnotationMethodSignupUser
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            errorDto.setListError(bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        }
        User userIsNew = authService.getUserByEmail(signupDTO.getEmail());
        if (userIsNew != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Такой пользователь уже существует");
        }

        int code = random.nextInt(10000,99999);

        try {
            emailService.sendEmail(
                    signupDTO.getEmail(),
                    PatternEmailMessage.SUBJECT.getTemplate(),
                    PatternEmailMessage.BODY.format(code)
            );
        }catch (Exception e){
            log.error("SMTP ERROR \n" + e.getMessage());
            return ResponseEntity.status(504).build();
        }

        setCacheAccountConformation.setCacheAccountConformation(code, signupDTO);

        return ResponseEntity.ok().build();
    }


    @SignupControllerAnnotationMethodAccountConfirmation
    @PostMapping("/account/confirmation")
    public ResponseEntity<?> accountConfirmation(@RequestParam("code") int code) {

        Optional<SignupDTO> signupDtoOpt = Optional.ofNullable(getCacheAccountConformationCache.getCacheAccountConformation(code));

        if (signupDtoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }

        UserDTO createdUser = authService.createUser(signupDtoOpt.get());
        if (createdUser == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не получилось создать пользователя");
        }
        setSubscription
                .setSubscription(
                        new SubscriptionModel(
                                authService.getUserByEmail(signupDtoOpt.get().getEmail()),
                                new Timestamp(System.currentTimeMillis()),
                                freeAttempt, Status.INACTIVE)
                );
        deleteCacheAccountConformation.deleteCacheAccountConformation(code);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
