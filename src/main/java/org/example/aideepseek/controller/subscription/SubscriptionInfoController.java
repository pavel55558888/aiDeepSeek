package org.example.aideepseek.controller.subscription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aideepseek.annotation.ApiResponseUnAuth;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.service.subscription.GetSubscriptionByEmail;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Информация о подписке пользователя")
public class SubscriptionInfoController {

    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(
            summary = "Получить информацию",
            description = "Вернет объект, который создается при регистрации и хранится в бд, статусы: ACTIVE,BLOCKED,INACTIVE"
    )
    @ApiResponseUnAuth
    @GetMapping("/subscription/online")
    public ResponseEntity<?> getSubscriptionUser(){

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        return ResponseEntity.ok().body(getSubscriptionByEmail.getSubscriptionByEmail(username));
    }
}
