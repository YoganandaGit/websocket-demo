package com.websockets.demo.client;

import com.websockets.demo.model.JobInfo;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
//TODO -- This code is not tested yet.
@Path("/extensions")
@RegisterRestClient(configKey = "extensions-api")
public interface ExtensionsService {
    @GET
    @Path("/systemId/{systemId}")
    Uni<List<JobInfo>> get(@PathParam("systemId") String systemId);
}
