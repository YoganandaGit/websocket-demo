package com.websockets.demo.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/jobs/{systemId}")
@ApplicationScoped
public class JobSocket {

    private static final Logger LOG = Logger.getLogger(JobSocket.class);

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @OnOpen
    public void onOpen(Session session, @PathParam("systemId") String systemId) {
        sessions.put(systemId, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("systemId") String systemId) {
        sessions.remove(systemId);
        broadcast("SystemId " + systemId + " Removed");
    }

    @OnError
    public void onError(Session session, @PathParam("systemId") String systemId, Throwable throwable) {
        sessions.remove(systemId);
        LOG.error("onError", throwable);
        broadcast("SystemId " + systemId + " removed on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("systemId") String systemId) {
        printRandomMessage(systemId);
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("SystemId " + systemId + " added");
        } else {
            broadcast(">> " + systemId + ": " + message);
        }
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    LOG.error("Unable to send message: {}", result.getException());
                }
            });
        });
    }

    private void printRandomMessage(String systemId) {
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

            // Construct and print the message
            if ("DEFAULT".equals(systemId)) {
                broadcast(UUID.randomUUID() + " " + phase + " " + process + " " + state + " ");
            } else {
                broadcast(UUID.randomUUID() + " " + phase + " " + process + " " + state + " " + systemId);
            }
        };
        // Schedule the task to run every 1 second
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

}
