package com.proton.domain.service

import com.proton.domain.models.User
import kotlinx.coroutines.CoroutineScope

interface UserService {
    suspend fun getUser(coroutineScope: CoroutineScope, id: Long): User
}