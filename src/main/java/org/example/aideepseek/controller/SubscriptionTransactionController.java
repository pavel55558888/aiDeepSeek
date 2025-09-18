package org.example.aideepseek.controller;

import org.example.aideepseek.database.service.GetTransactionByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionTransactionController {

    @Autowired
    private GetTransactionByEmail getTransactionByEmail;

    @GetMapping("/subscription/online/transaction/{username}")
    public ResponseEntity<?> getSubscriptionTransactionUser(@PathVariable String username){
        return ResponseEntity.ok().body(getTransactionByEmail.getTransaction(username));
    }
}
