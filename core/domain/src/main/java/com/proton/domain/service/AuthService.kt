package com.proton.domain.service

import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope

interface AuthService {
    suspend fun login(coroutineScope: CoroutineScope, email: String, password: String): Result<User, NetworkError.LoginError>
    suspend fun register(coroutineScope: CoroutineScope, user: User, password: String): Result<User, NetworkError.RegisterError>
    suspend fun authenticate(coroutineScope: CoroutineScope): Result<User, NetworkError.AuthError>
}