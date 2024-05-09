package com.websockets.demo.api;

import com.websockets.demo.model.JobInfo;
import com.websockets.demo.service.JobService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/jobs")
public class JobResource {

    private final JobService jobService;

    @Channel("job-updates")
    Emitter<JobInfo> jobUpdatesEmitter;

    @Inject
    public JobResource(JobService jobService) {
        this.jobService = jobService;
    }

    @GET
    @Path("/systemId/{systemId}")
    public Uni<List<JobInfo>> get(@PathParam("systemId") String systemId) {
        return jobService.listJobs(systemId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<JobInfo> create(JobInfo jobInfo) {
        return null;
    }

    @POST
    @Path("/kafka/push/{systemId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> pushJobsToKafka(@PathParam("systemId") String systemId) {
        List<JobInfo> jobs = jobService.prepareJobList(systemId);
        String jobsStr = jobs
                .stream()
                .peek(jobInfo -> jobUpdatesEmitter.send(jobInfo))
                .map(JobInfo -> String.join(",", JobInfo.getPhase(), JobInfo.getProcess(), JobInfo.getState(), JobInfo.getSystemId(), JobInfo.getJobId()))
                .collect(Collectors.joining(System.lineSeparator()));
        return Uni.createFrom().item(jobsStr);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Uni<JobInfo> update(@PathParam("id") long id, JobInfo department) {
        return null;
    }

    @GET
    @Path("/name/{name}")
    public Uni<JobInfo> getByName(@PathParam("name") String name) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("{id}")
    public Uni<JobInfo> get(@PathParam("id") long id) {
        return null;
    }

}
