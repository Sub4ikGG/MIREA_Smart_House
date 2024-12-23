package ru.chatan.smarthouse.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object SmartHouseHttpClient {

    private var smartHouseHttpClient: HttpClient = buildHttpClient()

    private fun buildHttpClient(
        user: String? = null,
        password: String? = null
    ): HttpClient {
        val httpClient = HttpClient(CIO) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            if (user != null && password != null)
                defaultRequest {
                    header("X-Username", user)
                    header("X-Password", password)
                }
        }

        return httpClient
    }

    fun rebuildHttpClient(user: String, password: String) {
        smartHouseHttpClient = buildHttpClient(user = user, password = password)
    }

    fun resolveHttpClient(): HttpClient = smartHouseHttpClient
}