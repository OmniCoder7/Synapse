package com.proton.data.remote

import com.proton.domain.models.User
import com.proton.domain.service.UserService
import com.proton.network.api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class UserServiceImpl(
    private val userApi: UserApi,
) : UserService {
    override suspend fun getUser(coroutineScope: CoroutineScope, id: Long): User {
        val res = coroutineScope.async { userApi.getUser(id) }.await()
        return User(
            userId = res.userId,
            firstName = res.firstName,
            lastName = res.lastName,
            email = res.email,
            gender = res.gender,
            dob = res.dob,
            age = res.age,
            userName = res.userName,
            number = res.number,
        )
    }
}