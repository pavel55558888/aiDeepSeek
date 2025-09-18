package org.example.aideepseek.aspect.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.aideepseek.aspect.SubscriptionSearchAspect;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.UpdateSubscription;
import org.example.aideepseek.dto.ErrorDto;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
@Order(2)
public class SubscriptionSearchAspectImpl implements SubscriptionSearchAspect {
    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;
    @Autowired
    private UpdateSubscription updateSubscription;
    @Autowired
    private JwtUtil jwtUtil;
    private Logger log = LoggerFactory.getLogger(SubscriptionSearchAspectImpl.class);

    @Around("execution(* org.example.aideepseek.controller.ChatController.chat(..))")
    @Override
    public Object searchSubscription(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Aspect searchSubscription");

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        SubscriptionModel subscriptionModel = CheckingSubscriptionPeriod(getSubscriptionByEmail.getSubscriptionByEmail(username));
        if (subscriptionModel == null) {
            log.error("Account " + username + " notfound");
            return ResponseEntity.status(500).body(new ErrorDto("Ошибка: аккакунт не найден"));
        }else if (subscriptionModel.getStatus() == Status.BLOCKED) {
            log.debug("Account " + username + " status " + Status.BLOCKED);
            return ResponseEntity.ok().body(new ErrorDto("Ошибка: ваш аккакунт заблокирован. " +
                    "Причина: многопользовательский аккаунт. " +
                    "Последнее время данный аккаунт использовался с разных ip адресов. " +
                    "Для разблокировки оформите подписку снова и не повторяйте ошибок"));
        } else if (subscriptionModel.getStatus().equals(Status.INACTIVE)){
            log.debug("Account " + username + " status " + Status.INACTIVE);
            if (subscriptionModel.getFreeAttempt() > 0){
                subscriptionModel.setFreeAttempt(subscriptionModel.getFreeAttempt() - 1);
                updateSubscription.updateSubscription(subscriptionModel);
                log.debug("Account " + username + " free attempt " + subscriptionModel.getFreeAttempt());
            }else {
                log.debug("Account " + username + " free attempt equals 0 " + subscriptionModel.getFreeAttempt());
                return ResponseEntity.ok().body(new ErrorDto("Ошибка: бесплатные попытки закончились. Оформите подписку"));
            }
        }

        log.debug("Account active subscription {} status {} ", username, subscriptionModel.getStatus());

        return joinPoint.proceed();
    }

    private SubscriptionModel CheckingSubscriptionPeriod(SubscriptionModel subscriptionModel){
        log.debug("Checking subscription period");
        if (!subscriptionModel.getStatus().equals(Status.ACTIVE)){
            return subscriptionModel;
        }

        Instant now = Instant.now();
        Instant subscriptionStart = subscriptionModel.getTimestamp().toInstant();

        long daysBetween = ChronoUnit.DAYS.between(subscriptionStart, now);

        if (daysBetween > 30) {
            log.debug("Subscription period exceeded 30 days. Days passed: {}", daysBetween);
            subscriptionModel.setStatus(Status.INACTIVE);
            updateSubscription.updateSubscription(subscriptionModel);
        } else {
            log.debug("Subscription is still valid. Days passed: {}", daysBetween);
        }

        return subscriptionModel;

    }
}
