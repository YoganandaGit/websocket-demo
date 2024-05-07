package com.websockets.demo.memcache;

import com.websockets.demo.websocket.PushJobsWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class MessageSubscriber implements MessageListener {
    private final String name;

    public MessageSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessageAdded(String key, String message) {
        if(StringUtils.isBlank(name) && !name.equals(key)) {
            PushJobsWebSocket.broadcast("[" + key + "] " + ": " + message);
        } else {
            PushJobsWebSocket.broadcast("[" + name + "] " + key + ": " + message);
        }

    }
}

