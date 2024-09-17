package com.proton.network.exception

sealed class NetworkException : Exception() {
    class ConnectionException() : NetworkException()
    class UnauthorizedException : NetworkException()
    class UnknownException() : NetworkException()
    class ConflictException() : NetworkException()
    class BadRequestException() : NetworkException()
    class NotFoundException() : NetworkException()
}