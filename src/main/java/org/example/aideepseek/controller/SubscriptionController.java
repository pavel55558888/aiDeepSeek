package org.example.aideepseek.controller;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.service.*;
import org.example.aideepseek.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseOfSubscription purchaseOfSubscription;

    @PostMapping("/subscription/online/start")
    public void startSubscription(){
        //получить id, value, created_at и сохранить в кэш id - key, из jwt токена достать юзернейм
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
