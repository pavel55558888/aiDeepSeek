package org.example.aideepseek.controller.subscription;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aideepseek.annotation.swagger.controller.subscription.SubscriptionInfoControllerAnnotation;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.service.subscription.GetSubscriptionByEmail;
import org.example.aideepseek.dto.SubscriptionDTO;
import org.example.aideepseek.dto.UserDTO;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Информация о подписке пользователя")
public class SubscriptionInfoController {

    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;
    @Autowired
    private JwtUtil jwtUtil;

    @SubscriptionInfoControllerAnnotation
    @GetMapping("/subscription/online")
    public ResponseEntity<?> getSubscriptionUser(){

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }


        SubscriptionModel subscriptionModel = getSubscriptionByEmail.getSubscriptionByEmail(username);

        UserDTO userDTO = new UserDTO(
                subscriptionModel.getUser().getId(),
                subscriptionModel.getUser().getEmail()
        );

        SubscriptionDTO SubscriptionDto = new SubscriptionDTO(
                subscriptionModel.getId(),
                userDTO,
                subscriptionModel.getTimestamp(),
                subscriptionModel.getFreeAttempt(),
                subscriptionModel.getStatus()
        );

        return ResponseEntity.ok().body(SubscriptionDto);
    }
}
