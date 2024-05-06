package com.websockets.demo.memcache;

import com.websockets.demo.websocket.PushJobsWebSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSubscriber implements MessageListener {
    private final String name;

    public MessageSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessageAdded(String key, String message) {
        PushJobsWebSocket.broadcast("[" + name + "] " + key + ": " + message);
    }
}

