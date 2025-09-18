package org.example.aideepseek.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.example.aideepseek.database.model.SubscriptionModel;

public interface SubscriptionSearchAspect {
    public Object searchSubscription(ProceedingJoinPoint joinPoint) throws Throwable;
}
