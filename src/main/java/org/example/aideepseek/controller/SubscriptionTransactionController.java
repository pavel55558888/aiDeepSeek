package org.example.aideepseek.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.aideepseek.database.service.GetTransactionByEmail;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionTransactionController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private GetTransactionByEmail getTransactionByEmail;

    @GetMapping("/subscription/online/transaction")
    public ResponseEntity<?> getSubscriptionTransactionUser(){
        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        return ResponseEntity.ok().body(getTransactionByEmail.getTransaction(username));
    }
}
