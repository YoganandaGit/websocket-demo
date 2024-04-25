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
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/api/v1/jobs")
public class JobResource {

    private final JobService jobService;

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
