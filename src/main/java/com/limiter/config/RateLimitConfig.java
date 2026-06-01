package com.limiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {
    @Value("${rate.limit.requests}")
    private int requests;

    @Value("${rate.limit.seconds}")
    private int seconds;
}
