package com.proton.domain.useCase

import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class RegisterUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(coroutineScope: CoroutineScope,user: User, password: String): Result<User, NetworkError.RegisterError> {
        delay(1000)
        return authService.register(coroutineScope,user, password)
    }

}
