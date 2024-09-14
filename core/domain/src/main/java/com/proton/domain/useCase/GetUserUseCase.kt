package com.proton.domain.useCase

import com.proton.domain.models.User
import com.proton.domain.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class GetUserUseCase(private val userService: UserService) {
    operator fun invoke(id: Long, coroutineScope: CoroutineScope): Deferred<User> {
        return coroutineScope.async {
            userService.getUser(coroutineScope, id)
        }
    }
}