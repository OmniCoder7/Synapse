package com.proton.network.exception

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.*
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.*

fun mapKtorExceptionToNetworkException(e: Throwable): NetworkException {
    return when (e) {
        is ConnectTimeoutException, is SocketTimeoutException -> NetworkException.ConnectionException(e)
        is ResponseException -> {
            when (e.response.status) {
                HttpStatusCode.Conflict -> NetworkException.ConflictException(e)
                HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException()
                HttpStatusCode.BadRequest -> NetworkException.BadRequestException(e)
                else -> NetworkException.UnknownException(e)
            }
        }
        else -> NetworkException.UnknownException(e)
    }
}