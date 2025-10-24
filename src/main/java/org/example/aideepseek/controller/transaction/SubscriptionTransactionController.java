package org.example.aideepseek.controller.transaction;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.aideepseek.annotation.swagger.controller.transaction.SubscriptionTransactionControllerAnnotation;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.service.transacion.GetTransactionByEmail;
import org.example.aideepseek.dto.TransactionSubscriptionDTO;
import org.example.aideepseek.dto.UserDTO;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Транзакции пользователя", description = "Просмотр всех транзакции пользователя")
public class SubscriptionTransactionController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private GetTransactionByEmail getTransactionByEmail;

    @SubscriptionTransactionControllerAnnotation
    @GetMapping("/subscription/online/transaction")
    public ResponseEntity<?> getSubscriptionTransactionUser(){
        String username = jwtUtil.getUsernameFromJwt();

        List<TransactionSubscriptionModel> transactionModels = getTransactionByEmail.getTransaction(username);

        List<TransactionSubscriptionDTO> transactionModelsDto = transactionModels.stream()
                .map(model -> {
                    UserDTO userDto = new UserDTO(
                            model.getUser().getId(),
                            model.getUser().getEmail()
                    );
                    return new TransactionSubscriptionDTO(userDto, model.getPrice(), model.getTimestamp());
                })
                .toList();

        return ResponseEntity.ok().body(transactionModelsDto);
    }
}
