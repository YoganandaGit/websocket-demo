package com.websockets.demo;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/dedicatedJob")
public class DedicatedJobSocket {
    private static ConcurrentHashMap<String, Session> clients = new ConcurrentHashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @OnOpen
    public void onOpen(Session session) {
        // Add session to the connected sessions set
        clients.put(session.getId(), session);
        System.out.println("New session opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Assume message format is "sessionId:systemId"
        String[] parts = message.split(":", 2);
        if (parts.length == 2) {
            String targetSessionId = parts[0];
            String systemId = parts[1];
            printRandomMessage(targetSessionId, systemId);
        }
    }

    @OnClose
    public void onClose(Session session) {
        // Remove session from the set
        clients.remove(session.getId());
        System.out.println("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Handle error
        System.out.println("Error on session " + session.getId() + ": " + throwable.getMessage());
    }

    private void printRandomMessage(String sessionId, String systemId) {
        Random random = new Random();
        // Generate a random index to select a message
        Runnable task = () -> {
            // Arrays of words to create messages
            String[] phases = { "TR", "LR", "LRP", "LI", "LIP", "TLI", "TLIP", "LLI", "LLIP", "TE", "LE" };
            String[] processes = { "AFIS" };
            String[] states = { "WAIT", "EXEC", "DONE" };

            // Generate random indices to select words
            String phase = phases[random.nextInt(phases.length)];
            String process = processes[random.nextInt(processes.length)];
            String state = states[random.nextInt(states.length)];

            String message = UUID.randomUUID() + " " + phase + " " + process + " " + state + " "+ systemId;

            Session targetSession = clients.get(sessionId);
            if (targetSession != null) {
                try {
                    targetSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Sent message to " + sessionId + ": " + message);
            } else {
                System.out.println("Session " + sessionId + " not found");
            }
        };
        // Schedule the task to run every 1 second
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
