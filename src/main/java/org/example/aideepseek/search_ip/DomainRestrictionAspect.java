package org.example.aideepseek.search_ip;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Set;

@Aspect
@Component
public class DomainRestrictionAspect {

    private Logger log = LoggerFactory.getLogger(DomainRestrictionAspect.class);

    @Value("${whitelist.by.subscription.domain}")
    private Set<String> ALLOWED_ORIGINS;

    @Around("execution(* org.example.aideepseek.controller.SubscriptionController.setSubscriptionUser(..))")
    public Object checkOrigin(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No HTTP request context found");
        }

        HttpServletRequest request = attributes.getRequest();
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");

        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            log.debug("Request accepted from allowed origin: {}", origin);
            return joinPoint.proceed();
        }

        if (referer != null) {
            for (String allowed : ALLOWED_ORIGINS) {
                if (referer.startsWith(allowed)) {
                    log.debug("Request accepted from allowed referer: {}", referer);
                    return joinPoint.proceed();
                }
            }
        }

        log.debug("Blocked request: Origin='{}', Referer='{}'", origin, referer);
        throw new IllegalArgumentException("Invalid domain origin");
    }
}