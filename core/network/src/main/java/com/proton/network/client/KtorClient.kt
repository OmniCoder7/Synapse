package com.proton.network.client

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.gson
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun create(): HttpClient = HttpClient(Android) {

    install(Logging) {
    }

    install(ContentNegotiation) { gson() }

    defaultRequest {
        url("http://172.17.5.123")
        port = 8080
    }
}