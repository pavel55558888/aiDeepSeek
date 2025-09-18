package org.example.aideepseek.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;

public interface RequestIpAspect {
    public Object logRequestIp(ProceedingJoinPoint joinPoint) throws Throwable;
}
