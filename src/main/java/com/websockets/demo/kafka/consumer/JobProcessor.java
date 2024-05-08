package com.websockets.demo.kafka.consumer;

import com.websockets.demo.memcache.MessageCache;
import com.websockets.demo.model.JobInfo;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.List;

@ApplicationScoped
public class JobProcessor {

    @Incoming("updates")
    @Outgoing("jobs")
    @Blocking
    public void process(JobInfo jobInfo) {
        // simulate some hard working task
         MessageCache.getInstance().addMessage(jobInfo.getJobId(), String.join(",", jobInfo.getPhase(), jobInfo.getProcess(), jobInfo.getState(), jobInfo.getSystemId()));
    }
}
