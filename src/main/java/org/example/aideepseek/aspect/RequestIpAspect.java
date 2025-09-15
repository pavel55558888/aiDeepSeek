package org.example.aideepseek.aspect;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.service.GetSubscriptionByEmail;
import org.example.aideepseek.database.service.UpdateSubscription;
import org.example.aideepseek.ignite.IgniteService;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Aspect
@Component
@Order(1)
public class RequestIpAspect {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IgniteService igniteService;
    @Autowired
    private GetSubscriptionByEmail getSubscriptionByEmail;
    @Autowired
    private UpdateSubscription updateSubscription;
    private Logger log = LoggerFactory.getLogger(RequestIpAspect.class);

    @Value("${check.ip.and.blocked}")
    private boolean checkIpAndBlocked;
    @Value("${maximum.count.users.per.account}")
    private int maximumUsersPerAccount;
    @PostConstruct
    public void init() {
        log.info("checkIpAndBlocked: {}", checkIpAndBlocked);
        log.info("maximumUsersPerAccount: {}", maximumUsersPerAccount);
    }

    @Around("execution(* org.example.aideepseek.controller.ChatController.chat(..))")
    public Object logRequestIp(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Aspect RequestIpAspect");
        if(checkIpAndBlocked) {
            String username = null;
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");
            String clientIp = getClientIp(request);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                username = jwtUtil.extractUsername(authHeader.substring(7));
            }

            log.debug("Request received from IP and username: {}, {}", clientIp, username);
            SubscriptionModel subscriptionModel = getSubscriptionByEmail.getSubscriptionByEmail(username);

            int size = igniteService.cacheIpGetAndPutElseNewAddress(username, clientIp);
            if (size > maximumUsersPerAccount) {
                subscriptionModel.setStatus(Status.BLOCKED);
                updateSubscription.updateSubscription(subscriptionModel);
            }
        }


        Object result = joinPoint.proceed();

        return result;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty() && !xfHeader.equals("unknown")) {
            return xfHeader.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        String remoteAddr = request.getRemoteAddr();
        if ("127.0.0.1".equals(remoteAddr) || "::1".equals(remoteAddr)) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.warn("Cannot resolve localhost IP", e);
            }
        }

        return remoteAddr;
    }
}
