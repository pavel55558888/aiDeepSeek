package org.example.aideepseek.aspect.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.aideepseek.aspect.SubscriptionSearchAspect;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.subscription.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.subscription.UpdateSubscription;
import org.example.aideepseek.dto.ErrorDTO;
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

    @Around("execution(* org.example.aideepseek.controller.deepseek.DeepSeekController.chat(..))")
    @Override
    public Object searchSubscriptionChat(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Aspect searchSubscription chat");

        String username = jwtUtil.getUsernameFromJwt();

        SubscriptionModel subscriptionModel = checkingSubscriptionPeriod(getSubscriptionByEmail.getSubscriptionByEmail(username));
        if (subscriptionModel == null) {
            log.error("Account " + username + " notfound");
            return ResponseEntity.status(500).body(new ErrorDTO("Ошибка: аккакунт не найден"));
        }else if (subscriptionModel.getStatus() == Status.BLOCKED) {
            log.debug("Account " + username + " status " + Status.BLOCKED);
            return ResponseEntity.ok().body(new ErrorDTO("Ошибка: ваш аккакунт заблокирован. " +
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
                return ResponseEntity.ok().body(new ErrorDTO("Ошибка: бесплатные попытки закончились. Оформите подписку"));
            }
        }

        log.debug("Account active subscription {} status {} ", username, subscriptionModel.getStatus());

        return joinPoint.proceed();
    }

    @Around("execution(* org.example.aideepseek.controller.subscription.SubscriptionInfoController.getSubscriptionUser(..))")
    public Object searchSubscriptionInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Aspect searchSubscription info");

        String username = jwtUtil.getUsernameFromJwt();
        checkingSubscriptionPeriod(getSubscriptionByEmail.getSubscriptionByEmail(username));


        return joinPoint.proceed();
    }


    private SubscriptionModel checkingSubscriptionPeriod(SubscriptionModel subscriptionModel){
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
