package com.redhat.demo.servicemesh.basic.sample

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.Builder
import java.net.http.HttpResponse.BodyHandlers

interface HttpTemplate<DATA_TYPE> {
    fun jsonGet(
        path: String, headers: Map<String, String> = emptyMap(), jsonMapper: (jsonRaw: String) -> DATA_TYPE
    ): DATA_TYPE
}

class JavaRestHttpTemplate<DATA_TYPE>(
    private val baseUrl: String
) : HttpTemplate<DATA_TYPE> {
    private val client = HttpClient.newBuilder().build()

    override fun jsonGet(path: String, headers: Map<String, String>, jsonMapper: (jsonRaw: String) -> DATA_TYPE): DATA_TYPE {
        val url = "$baseUrl$path"
        println("start HTTP GET $url")
        return processRequest(url, emptyMap(), client, jsonMapper) { builder: Builder ->
            headers.forEach { builder.setHeader(it.key, it.value) }
            builder.GET().build()
        }
    }

    private fun <DATA_TYPE> processRequest(
        baseUrl: String, requestParams: Map<String, Any>, client: HttpClient, jsonMapper: (jsonRaw: String) -> DATA_TYPE, requestBuild: (builder: Builder) -> HttpRequest
    ): DATA_TYPE {
        val url = baseUrl + if (baseUrl.contains("?")) "&" else "?" + requestParams.map { e -> "${e.key}=${e.value.toString()}" }.joinToString("&")
        println("start HTTP request $url")
        val requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))

        val request = requestBuild(requestBuilder)
        val response = client.send(request, BodyHandlers.ofString())
        println("got HTTP response ${response.statusCode()}")
        return jsonMapper(response.body())
    }
}