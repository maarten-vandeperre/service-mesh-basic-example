package com.redhat.demo.servicemesh.basic.sample

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed

@Path("/hello")
class SampleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "Service C - Hello - Counter", description = "the get data implementation for service C")
    @Timed(name = "Service C - Hello - Timer", description = "the get data implementation for service C")
    fun getData(): Response {
        return Response.ok("Service C says hello - V1").build()
    }
}