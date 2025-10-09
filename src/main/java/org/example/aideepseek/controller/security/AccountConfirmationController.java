package org.example.aideepseek.controller.security;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.subscription.SetSubscription;
import org.example.aideepseek.dto.SignupDTO;
import org.example.aideepseek.dto.UserDTO;
import org.example.aideepseek.ignite.service.account_conformation.DeleteCacheAccountConformation;
import org.example.aideepseek.ignite.service.account_conformation.GetCacheAccountConformationCache;
import org.example.aideepseek.security.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1")
public class AccountConfirmationController {
    @Autowired
    private SetSubscription setSubscription;
    @Value("${free.attempt.user}")
    private int freeAttempt;
    @Autowired
    private GetCacheAccountConformationCache getCacheAccountConformationCache;
    @Autowired
    private DeleteCacheAccountConformation deleteCacheAccountConformation;
    @Autowired
    private AuthService authService;

    @PostMapping("/account/confirmation")
    public ResponseEntity<?> accountConfirmation(@RequestParam("code") int code) {

        Optional<SignupDTO> signupDtoOpt = Optional.ofNullable(getCacheAccountConformationCache.getCacheAccountConformation(code));

        if (signupDtoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }

        UserDTO createdUser = authService.createUser(signupDtoOpt.get());
        if (createdUser == null){
            return new ResponseEntity<>("Не получилось создать пользователя", HttpStatus.BAD_REQUEST);
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
