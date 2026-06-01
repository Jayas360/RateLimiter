package com.limiter.exception;

public class RateLimitExceededException extends Exception{
    public RateLimitExceededException(String message) {
        super(message);
        System.out.println("Rate Limit exceeded");
    }
}
