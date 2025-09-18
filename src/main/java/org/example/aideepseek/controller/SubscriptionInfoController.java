package org.example.aideepseek.controller;

import org.example.aideepseek.database.service.GetSubscriptionByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionInfoController {

    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;

    @GetMapping("/subscription/online/{username}")
    public ResponseEntity<?> getSubscriptionUser(@PathVariable String username){
        return ResponseEntity.ok().body(getSubscriptionByEmail.getSubscriptionByEmail(username));
    }
}
