package com.proton.data.remote

import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import com.proton.network.api.UserApi
import com.proton.network.exception.NetworkException
import com.proton.network.repositories.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class AuthServiceImpl(
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
) : AuthService {
    override suspend fun login(
        coroutineScope: CoroutineScope,
        email: String,
        password: String,
    ): Result<User, NetworkError.LoginError> {
        return try {
            val res = coroutineScope.async {
                val res = userApi.login(email = email, password = password)
                coroutineScope.launch { tokenManager.saveAccessToken(res.accessToken) }
                coroutineScope.launch { tokenManager.saveRefreshToken(res.refreshToken) }
                res
            }.await()

            Result.Success(
                User(
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
            )
        } catch (e: NetworkException) {
            when (e) {
                is NetworkException.BadRequestException -> Result.Error(NetworkError.LoginError.UNKNOWN) // this exception will never occur
                is NetworkException.ConflictException -> Result.Error(NetworkError.LoginError.UNKNOWN) // this exception will never occur
                is NetworkException.ConnectionException -> Result.Error(NetworkError.LoginError.CONNECTION)
                is NetworkException.UnauthorizedException -> Result.Error(NetworkError.LoginError.UNAUTHORIZED)
                is NetworkException.UnknownException -> Result.Error(NetworkError.LoginError.UNKNOWN) // this exception will never occur
            }
        }
    }

    override suspend fun register(
        coroutineScope: CoroutineScope,
        user: User,
        password: String,
    ): Result<User, NetworkError.RegisterError> {
        return try {
            val res = coroutineScope.async {
                val res = userApi.register(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    password = password,
                    gender = user.gender,
                    dob = user.dob,
                    userName = user.userName,
                    number = user.number,
                    address = user.address
                )

                tokenManager.saveAccessToken(res.accessToken)
                tokenManager.saveRefreshToken(res.refreshToken)
                res
            }.await()
            Result.Success(
                User(
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
            )
        } catch (e: NetworkException) {
            when (e) {
                is NetworkException.BadRequestException -> Result.Error(NetworkError.RegisterError.BAD_REQUEST)
                is NetworkException.ConflictException -> Result.Error(NetworkError.RegisterError.CONFLICT)
                is NetworkException.ConnectionException -> Result.Error(NetworkError.RegisterError.CONNECTION)
                is NetworkException.UnauthorizedException -> Result.Error(NetworkError.RegisterError.UNKNOWN_ERROR)
                is NetworkException.UnknownException -> Result.Error(NetworkError.RegisterError.UNKNOWN_ERROR)
            }
        }
    }

    override suspend fun authenticate(coroutineScope: CoroutineScope): Result<User, NetworkError.AuthError> {
        val res = coroutineScope.async {
            val token = tokenManager.getAccessToken()
                ?: throw NullPointerException()
            userApi.authenticate(token)
        }.await()
        return Result.Success(
            User(
                firstName = res.firstName,
                lastName = res.lastName,
                email = res.email,
                gender = res.gender,
                dob = res.dob,
                age = res.age,
                userName = res.userName,
                number = res.number,
                cardId = res.cardId,
            )
        )
    }
}