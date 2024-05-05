package com.websockets.demo.memcache.socket;

import com.websockets.demo.websocket.JobSocket;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ServerEndpoint("/pushJobs/{workstationId}")
@ApplicationScoped
public class PushJobsWebSocket {

    private static final Logger LOG = Logger.getLogger(JobSocket.class);

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @OnOpen
    public void onOpen(Session session, @PathParam("workstationId") String workstationId) {
        sessions.put(workstationId, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("workstationId") String workstationId) {
        sessions.remove(workstationId);
        broadcast("workstationId " + workstationId + " Removed");
    }

    @OnError
    public void onError(Session session, @PathParam("workstationId") String workstationId, Throwable throwable) {
        sessions.remove(workstationId);
        LOG.error("onError", throwable);
        broadcast("workstationId " + workstationId + " removed on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("workstationId") String workstationId) {
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("workstationId " + workstationId + " added");
        } else {
            broadcast(">> " + workstationId + ": " + message);
        }
    }

    public static void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    LOG.error("Unable to send message: {}", result.getException());
                }
            });
        });
    }
}
