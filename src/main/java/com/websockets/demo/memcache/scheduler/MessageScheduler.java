package com.websockets.demo.memcache.scheduler;

import com.websockets.demo.memcache.MessageCache;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class MessageScheduler {
    private int messageCount = 0;

    @Scheduled(every = "36000s") // Adjust the frequency as needed
    public void pushMessageToCache() {
        String key = String.valueOf(++messageCount);
        String message = "Scheduled message at " + LocalDateTime.now();
        MessageCache.getInstance().addMessage(key, message);
        log.info("Pushed message to cache: {}", message);
    }
}
