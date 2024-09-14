package com.proton.network.exception

sealed class NetworkException : Exception() {
    class ConnectionException(cause: Throwable?) : NetworkException()
    class UnauthorizedException : NetworkException()
    class UnknownException(cause: Throwable?) : NetworkException()
    class ConflictException(cause: Throwable?) : NetworkException()
    class BadRequestException(cause: Throwable?) : NetworkException()
}