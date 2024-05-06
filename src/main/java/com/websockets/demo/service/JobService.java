package com.websockets.demo.service;

import com.websockets.demo.memcache.MessageCache;
import com.websockets.demo.model.JobInfo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class JobService {

    public Uni<List<JobInfo>> listJobs(String systemId) {
        Random random = new Random();
        String[] phases = { "TR", "LR", "LRP", "LI", "LIP", "TLI", "TLIP", "LLI", "LLIP", "TE", "LE" };
        String[] processes = { "AFIS" };
        String[] states = { "WAIT", "EXEC", "DONE" };

        // Generate random indices to select words
        String phase = phases[random.nextInt(phases.length)];
        String process = processes[random.nextInt(processes.length)];
        String state = states[random.nextInt(states.length)];
        String jobId = UUID.randomUUID().toString();

        JobInfo jobInfo = new JobInfo(phase, process, state, systemId, jobId);

        //Push the message into the cache
        MessageCache.getInstance().addMessage(jobId, String.join(",", phase, process, state, systemId));

        return Uni.createFrom().item(List.of(jobInfo));
    }

}
