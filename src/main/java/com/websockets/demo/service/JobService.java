package com.websockets.demo.service;

import com.google.common.collect.Lists;
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
        List<JobInfo> jobList = Lists.newArrayList();

        //iterate this block 10 times using the lambda expression
        //to generate 10 random job info
        for (int i = 0; i < 10; i++) {
            // Generate random indices to select words
            String phase = phases[random.nextInt(phases.length)];
            String process = processes[random.nextInt(processes.length)];
            String state = states[random.nextInt(states.length)];
            String jobId = UUID.randomUUID().toString();
            JobInfo jobInfo = new JobInfo(phase, process, state, systemId, jobId);
            jobList.add(jobInfo);
        }
        //Push the message into the cache
        jobList
                .forEach(jobInfo -> MessageCache.getInstance().addMessage(jobInfo.getJobId(), String.join(",", jobInfo.getPhase(), jobInfo.getProcess(), jobInfo.getState(), systemId)));

        //Return the list of job info
        return Uni.createFrom().item(jobList);
    }

}
