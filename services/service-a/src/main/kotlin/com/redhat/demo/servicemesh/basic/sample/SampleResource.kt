package com.redhat.demo.servicemesh.basic.sample

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.ConfigProvider
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed

@Path("/hello")
class SampleResource {

    private val serviceBHttpTemplate =
        JavaRestHttpTemplate<List<String>>(ConfigProvider.getConfig().getConfigValue("demo.service-b.base-url").value)
    private val serviceCHttpTemplate =
        JavaRestHttpTemplate<List<String>>(ConfigProvider.getConfig().getConfigValue("demo.service-c.base-url").value)

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "servcicea_svc:counter", description = "the get data implementation for service A")
    @Timed(name = "servicea:svc:timer", description = "the get data implementation for service A", unit = MetricUnits.MILLISECONDS)
    fun getData(): Response {
        val bResult = try{
            serviceBHttpTemplate.jsonGet("/hello", emptyMap()){ listOf(it) }
        } catch (e: Exception){
            listOf("Service B request failed: ${e.localizedMessage}")
        }
        val cResult = try{
            serviceCHttpTemplate.jsonGet("/hello", emptyMap()){ listOf(it) }
        } catch (e: Exception){
            listOf("Service C request failed: ${e.localizedMessage}")
        }
        return Response.ok(bResult + cResult + listOf("Service A says hello - V1")).build()
    }
}