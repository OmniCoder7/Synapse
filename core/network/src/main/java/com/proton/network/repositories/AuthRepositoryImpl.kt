package com.proton.network.repositories

import com.proton.network.api.UserApi
import com.proton.network.models.request.LoginRequest
import com.proton.network.models.request.RegisterRequest
import com.proton.network.models.response.AuthResponse
import com.proton.network.models.response.LoginResponse

class AuthRepositoryImpl(
    private val userApi: UserApi
): AuthRepository {
    override fun authenticate(): String {
        return ""
    }

    override fun refreshTokens(): AuthResponse {
        return AuthResponse(
            firstName = "",
            lastName = "",
            email = "",
            gender = "",
            dob = "",
            userName = "",
            number = 0,
            userId = 0,
            loginPassword = "",
            cartProducts = listOf(),
            orderIds = listOf(),
            wishListIds = listOf()

        )
    }

    override fun login(loginRequest: LoginRequest): LoginResponse {
        return LoginResponse("","")
    }

    override fun register(registerRequest: RegisterRequest): AuthResponse {
        return AuthResponse(
            firstName = "",
            lastName = "",
            email = "",
            gender = "",
            dob = "",
            userName = "",
            number = 0,
            userId = 0,
            loginPassword = "",
            cartProducts = listOf(),
            orderIds = listOf(),
            wishListIds = listOf()

        )

    }
}