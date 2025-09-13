package org.example.aideepseek.search_ip;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Aspect
@Component
public class RequestIpAspect {

    @Autowired
    private JwtUtil jwtUtil;
    private Logger log = LoggerFactory.getLogger(RequestIpAspect.class);

    @Value("${check.ip.and.blocked}")
    private boolean checkIpAndBlocked;
    @Value("${maximum.count.users.per.account}")
    private int maximumUsersPerAccount;

    @Around("execution(* org.example.aideepseek.controller.ChatController.chat(..))")
    public Object logRequestIp(ProceedingJoinPoint joinPoint) throws Throwable {
        if(checkIpAndBlocked) {
            String token = null;
            String username = null;

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String authHeader = request.getHeader("Authorization");
            String clientIp = getClientIp(request);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtil.extractUsername(token);
            }

            log.info("Request received from IP and username: {}, {}", clientIp, username);

            //добавить логику сохранения логина + ip и блокировку акккаунта, если кол-во юзеров на аккаунт больше maximumUsersPerAccount
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
