package com.websockets.demo.memcache.config;

import com.websockets.demo.memcache.MessageCache;
import com.websockets.demo.memcache.MessageSubscriber;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class AppLifecycleBean {

    void onStart(@Observes StartupEvent ev) {
        MessageCache cache = MessageCache.getInstance();
        cache.addListener(new MessageSubscriber(""));
    }
}
