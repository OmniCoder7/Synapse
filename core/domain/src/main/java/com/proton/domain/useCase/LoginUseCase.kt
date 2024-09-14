package com.proton.domain.useCase

import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class LoginUseCase(
    private val authService: AuthService,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        coroutineScope: CoroutineScope
    ): Result<User, NetworkError.LoginError> {
        delay(1_000)
        return authService.login(coroutineScope,email, password)
    }
}
