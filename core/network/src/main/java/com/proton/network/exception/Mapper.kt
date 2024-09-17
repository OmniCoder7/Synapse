package com.proton.network.exception

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.*
import io.ktor.http.HttpStatusCode

fun mapKtorExceptionToNetworkException(e: Throwable): NetworkException {
    return when (e) {
        is ConnectTimeoutException, is SocketTimeoutException -> NetworkException.ConnectionException()
        is ResponseException -> {
            when (e.response.status) {
                HttpStatusCode.Conflict -> NetworkException.ConflictException()
                HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException()
                HttpStatusCode.BadRequest -> NetworkException.BadRequestException()
                else -> NetworkException.UnknownException()
            }
        }
        else -> NetworkException.UnknownException()
    }
}