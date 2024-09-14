package com.proton.domain.exception

class UserNotAuthenticated: Exception() {
    override val message: String
        get() = "User not authenticated"
}