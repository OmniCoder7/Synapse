package com.proton.domain.error

sealed interface NetworkError : Error {
    enum class LoginError : NetworkError {
        UNAUTHORIZED,
        CONNECTION,
        UNKNOWN
    }

    enum class RegisterError : NetworkError {
        UNKNOWN_ERROR,
        BAD_REQUEST,
        CONNECTION,
        CONFLICT
    }

    enum class AuthError : NetworkError {
        UNAUTHORIZED,
        UNKNOWN,
        TOO_MANY_REQUESTS
    }

    enum class ProductError: NetworkError {
        PRODUCT_NOT_FOUND,
        UNKNOWN_ERROR
    }
}