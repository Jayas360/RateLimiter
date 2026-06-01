package com.limiter.aspects;

import com.limiter.config.RateLimitConfig;
import com.limiter.custom_annotations.RateLimited;
import com.limiter.exception.RateLimitExceededException;
import com.limiter.services.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private RateLimitConfig rateLimitConfig;

    @Autowired
    private RateLimiter rateLimiter;

    @Around("@annotation(rateLimited)")
    public Object applyRateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        String key = getKey(joinPoint);

        System.out.println("Seconds: " + rateLimited.seconds());
        System.out.println("Requests: " + rateLimited.requests());

        if(!rateLimiter.tryAcquire(key)) {
            throw new RateLimitExceededException("Rate limit exceedeed");
        }

        return joinPoint.proceed();
    }

    private String getKey(ProceedingJoinPoint joinPoint) {
        return "123";
    }
}
