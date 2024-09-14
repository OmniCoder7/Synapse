package com.proton.network.repositories

import com.proton.network.models.request.LoginRequest
import com.proton.network.models.request.RegisterRequest
import com.proton.network.models.response.AuthResponse
import com.proton.network.models.response.LoginResponse

interface AuthRepository {
    fun authenticate(): String
    fun refreshTokens(): AuthResponse
    fun login(loginRequest: LoginRequest): LoginResponse
    fun register(registerRequest: RegisterRequest): AuthResponse
}