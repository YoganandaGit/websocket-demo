package com.websockets.demo.client;

import com.websockets.demo.model.JobInfo;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
//TODO -- This code is not tested yet.
@Path("/extension")
public class ExtensionsResource {

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @GET
    @Path("/systemId/{systemId}")
    public Uni<List<JobInfo>> get(@PathParam("systemId") String systemId) {
        return extensionsService.get(systemId);
    }
}
