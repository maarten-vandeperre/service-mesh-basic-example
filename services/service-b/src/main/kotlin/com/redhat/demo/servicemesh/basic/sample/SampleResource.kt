package com.redhat.demo.servicemesh.basic.sample

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed

@Path("/hello")
class SampleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "servciceb_svc:counter", description = "the get data implementation for service B")
    @Timed(name = "serviceb:svc:timer", description = "the get data implementation for service B", unit = MetricUnits.MILLISECONDS)
    fun getData(): Response {
        return Response.ok("Service B says hello").build()
    }
}