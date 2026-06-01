package com.limiter.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Component
public class RateLimiter {
    @Value("${rate.limit.requests}")
    private int requests;

    @Value("${rate.limit.seconds}")
    private int seconds;
    private final Map<String, RateLimitedSemaphore> semaphores = new ConcurrentHashMap<>();

    public boolean tryAcquire(String key) {
        // Get the current timestamp
        long currentTime = System.currentTimeMillis();

        // Start time for the current time window
        long windowStartTime = currentTime - (long)seconds * 1000;

        // removing the expired entries from semaphore map for particular keys
        cleanupExpiredEntries(windowStartTime);

        RateLimitedSemaphore semaphore = semaphores.computeIfAbsent(key, k -> {
            RateLimitedSemaphore semaphore1 = new RateLimitedSemaphore(requests);
            semaphore1.updateLastAcquiredTime(currentTime);
            return semaphore1;
        });

        if(semaphore.tryAcquire()) {
            semaphore.updateLastAcquiredTime(currentTime);
            return true;
        }

        return false;
    }


    private void cleanupExpiredEntries(long startTime) {
        Iterator<Map.Entry<String, RateLimitedSemaphore>> iterator = semaphores.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, RateLimitedSemaphore> semaphoreEntry = iterator.next();
            RateLimitedSemaphore semaphore =  semaphoreEntry.getValue();

            if(semaphore.getLastAcquiredTime() < startTime) {
                iterator.remove();
            }
        }
    }



}

class RateLimitedSemaphore extends Semaphore {

    private volatile long lastAcquiredTime;

    public RateLimitedSemaphore(int requests) {
        super(requests);
    }

    public void updateLastAcquiredTime(long time) {
        this.lastAcquiredTime = time;
    }

    public long getLastAcquiredTime() {
        return this.lastAcquiredTime;
    }
}