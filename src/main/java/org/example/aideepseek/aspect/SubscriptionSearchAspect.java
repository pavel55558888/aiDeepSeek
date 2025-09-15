package org.example.aideepseek.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.UpdateSubscription;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(2)
public class SubscriptionSearchAspect {
    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;
    @Autowired
    private UpdateSubscription updateSubscription;
    @Autowired
    private JwtUtil jwtUtil;
    private Logger log = LoggerFactory.getLogger(SubscriptionSearchAspect.class);

    @Around("execution(* org.example.aideepseek.controller.ChatController.chat(..))")
    public Object searchSubscription(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Aspect searchSubscription");

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        SubscriptionModel subscriptionModel = getSubscriptionByEmail.getSubscriptionByEmail(username);
        if (subscriptionModel == null) {
            log.error("Account " + username + " notfound");
            return ResponseEntity.status(500).body("Ошибка: аккакунт не найден");
        }else if (subscriptionModel.getStatus() == Status.BLOCKED) {
            log.debug("Account " + username + " status " + Status.BLOCKED);
            return ResponseEntity.ok().body("Ошибка: ваш аккакунт заблокирован. " +
                    "Причина: многопользовательский аккаунт. " +
                    "Последнее время данный аккаунт использовался с разных ip адресов");
        } else if (subscriptionModel.getStatus().equals(Status.INACTIVE)){
            log.debug("Account " + username + " status " + Status.INACTIVE);
            if (subscriptionModel.getFreeAttempt() > 0){
                subscriptionModel.setFreeAttempt(subscriptionModel.getFreeAttempt() - 1);
                updateSubscription.updateSubscription(subscriptionModel);
                log.debug("Account " + username + " free attempt " + subscriptionModel.getFreeAttempt());
            }else {
                log.debug("Account " + username + " free attempt equals 0 " + subscriptionModel.getFreeAttempt());
                return ResponseEntity.ok().body("Ошибка: бесплатные попытки закончились. Оформите подписку");
            }
        }

        log.debug("Account active subscription {} status {} ", username, subscriptionModel.getStatus());

        return joinPoint.proceed();
    }
}
