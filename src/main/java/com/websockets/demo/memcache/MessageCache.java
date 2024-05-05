package com.websockets.demo.memcache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.CopyOnWriteArrayList;

public class MessageCache {
    private static class Holder {
        private static final MessageCache INSTANCE = new MessageCache();
    }

    private final Cache<String, String> cache;
    private final CopyOnWriteArrayList<MessageListener> listeners;

    private MessageCache() {
        this.cache = CacheBuilder.newBuilder().build();
        this.listeners = new CopyOnWriteArrayList<>();
    }

    public static MessageCache getInstance() {
        return Holder.INSTANCE;
    }

    public void addMessage(String key, String message) {
        cache.put(key, message);
        notifyListenersAndRemove(key, message);
    }

    public void addListener(MessageListener listener) {
        listeners.addIfAbsent(listener);
    }

    private void notifyListenersAndRemove(String key, String message) {
        for (MessageListener listener : listeners) {
            listener.onMessageAdded(key, message);
        }
        cache.invalidate(key); // Remove the message after notifying all listeners
    }
}
