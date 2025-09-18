package org.example.aideepseek.controller;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.service.*;
import org.example.aideepseek.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetTransactionByEmail getTransactionByEmail;
    @Autowired
    private PurchaseOfSubscription purchaseOfSubscription;
    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;

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

    @PostMapping("/subscription/online/start")
    public void startSubscription(){
        //получить id, value, created_at и сохранить в кэш id - key, из jwt токена достать юзернейм
    }

    @GetMapping("/subscription/online/transaction/{username}")
    public ResponseEntity<?> getSubscriptionTransactionUser(@PathVariable String username){
        return ResponseEntity.ok().body(getTransactionByEmail.getTransaction(username));
    }

    @GetMapping("/subscription/online/{username}")
    public ResponseEntity<?> getSubscriptionUser(@PathVariable String username){
        return ResponseEntity.ok().body(getSubscriptionByEmail.getSubscriptionByEmail(username));
    }
}
