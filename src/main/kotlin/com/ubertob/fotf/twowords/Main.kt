package com.ubertob.fotf.twowords

import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val port = 8081
    println("TwoWords started: http://localhost:$port/")
    twoWords.asServer(Jetty(port)).start()
}