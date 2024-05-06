package com.websockets.demo.memcache;

public interface MessageListener {
    void onMessageAdded(String key, String message);
}
