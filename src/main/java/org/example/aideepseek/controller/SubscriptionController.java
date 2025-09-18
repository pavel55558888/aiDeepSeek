package org.example.aideepseek.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.aideepseek.dto.SubscriptionInfoStartDto;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.service.*;
import org.example.aideepseek.ignite.IgniteService;
import org.example.aideepseek.dto.ErrorDto;
import org.example.aideepseek.security.repositories.UserRepository;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseOfSubscription purchaseOfSubscription;
    @Autowired
    private IgniteService igniteService;
    @Autowired
    private JwtUtil jwtUtil;

    private ErrorDto errorDto = new ErrorDto();

    private Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    @PostMapping("/subscription/online/start")
    public ResponseEntity<?> startSubscription(@Valid @RequestBody SubscriptionInfoStartDto subscriptionInfoStartDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            errorDto.setListError(bindingResult.getAllErrors());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        subscriptionInfoStartDto.setUsername(username);

        igniteService.setSubscriptionInfo(subscriptionInfoStartDto.getId(), subscriptionInfoStartDto);

        log.debug("Start subscription " + subscriptionInfoStartDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscription/online/end")
    public void setSubscriptionUser(){
        String email = "p-vikulinpb@mail.ru";//демо
        double price = 123545.2245;//демо

        //уведомление об успешной оплате сравнивать с кэшем, если все ок, то поллучает подписку

        purchaseOfSubscription
                .purchaseOfSubscription(
                        new TransactionSubscriptionModel(userRepository.findFirstByEmail(email), price)
                );
    }
}
