package com.websockets.demo.kafka.consumer;

import com.websockets.demo.model.JobInfo;
import com.websockets.demo.websocket.PushJobsWebSocket;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class JobProcessor {

    @Incoming("updates")
    @Outgoing("jobs")
    @Blocking
    public void process(JobInfo jobInfo) {
        PushJobsWebSocket.broadcast("[" + jobInfo.getJobId() + "] " + ": " + String.join(",", jobInfo.getPhase(), jobInfo.getProcess(), jobInfo.getState(), jobInfo.getSystemId()));
    }
}
