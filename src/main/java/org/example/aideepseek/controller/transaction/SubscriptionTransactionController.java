package org.example.aideepseek.controller.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.service.transacion.GetTransactionByEmail;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Транзакции пользователя", description = "Просмотр всех транзакции пользователя")
public class SubscriptionTransactionController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private GetTransactionByEmail getTransactionByEmail;

    @Operation(summary = "Получить список всех транзакции", description = "Все произведенные оплаты пользователя вернутся, или пустой список")
    @ApiResponses()
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
