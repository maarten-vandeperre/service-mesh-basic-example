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
    @Counted(name = "Service A - Hello - Counter", description = "the get data implementation for service A")
    @Timed(name = "Service A - Hello - Timer", description = "the get data implementation for service A")
    fun getData(): Response {
        return Response.ok("Service A says hello").build()
    }
}