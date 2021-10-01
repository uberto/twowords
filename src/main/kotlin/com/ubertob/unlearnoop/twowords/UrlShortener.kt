package com.ubertob.unlearnoop.twowords

import com.ubertob.unlearnoop.twowords.Words.toShortId
import com.ubertob.unlearnoop.twowords.Words.toShortUrl
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer


fun main() {
    val port = 8081
    println("TwoWords started listening on port $port")
    twoWords.asServer(Jetty(port)).start()
}

val twoWords: HttpHandler = routes(
    "/generate" bind GET to ::generate,
    "/x/{short}" bind GET to ::expand
)

private fun expand(req: Request): Response =
    req.path("short")
        ?.let(::ShortUrl)
        ?.let(::toShortId)
        ?.let(RedisPersistence::retrieveUrl)
        ?.let(::htmlFullUrlPage)
        ?.let(::toResponse)
        ?: Response(NOT_FOUND).body("Url not found")


private fun generate(req: Request): Response =
    req.queries("url")
        .firstOrNull()
        ?.let(::FullUrl)
        ?.let(RedisPersistence::saveUrl)
        ?.let(::toShortUrl)
        ?.let(::htmlShortenedPage)
        ?.let(::toResponse)
        ?: Response(NOT_FOUND).body("Url not specified")


fun toResponse(htmlPage: HtmlPage): Response =
    Response(Status.OK).body(htmlPage.raw)

data class ShortUrl(val raw: String)
data class FullUrl(val raw: String)
data class ShortId(val number: Int) {
    val key: String = number.toString()
}



