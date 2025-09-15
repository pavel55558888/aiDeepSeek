package org.example.aideepseek.controller;

import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.service.*;
import org.example.aideepseek.security.repositories.UserRepository;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    @Autowired
    private SetTransaction setTransaction;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetTransactionByEmail getTransactionByEmail;
    @Autowired
    private PurchaseOfSubscription purchaseOfSubscription;
    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;

    @PostMapping("/subscription/online")
    public void setSubscriptionUser(){
        String email = "p-vikulinpb@mail.ru";//демо
        double price = 123545.2245;//демо

        purchaseOfSubscription
                .purchaseOfSubscription(
                        new TransactionSubscriptionModel(userRepository.findFirstByEmail(email), price)
                );
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
