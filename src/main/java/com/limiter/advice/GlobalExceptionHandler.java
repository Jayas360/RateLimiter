package com.limiter.advice;

import com.limiter.exception.RateLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle custom RateLimiter exception
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleLimitExceedException(Exception exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(message);
    }

}
