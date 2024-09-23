package com.proton.data.remote

import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import com.proton.domain.util.Result.*
import com.proton.network.api.UserApi
import com.proton.network.exception.NetworkException
import com.proton.network.repositories.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class AuthServiceImpl(
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
) : AuthService {
    override suspend fun login(
        coroutineScope: CoroutineScope,
        email: String,
        password: String,
    ): Result<User, NetworkError.LoginError> = coroutineScope {
        try {
            val res = userApi.login(email = email, password = password)

            launch { tokenManager.saveAccessToken(res.accessToken) }
            launch { tokenManager.saveRefreshToken(res.refreshToken) }

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
            Error(
                when (e) {
                    is NetworkException.ConnectionException -> NetworkError.LoginError.CONNECTION
                    is NetworkException.UnauthorizedException -> NetworkError.LoginError.UNAUTHORIZED
                    else -> NetworkError.LoginError.UNKNOWN
                }
            )
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
                    password = hashPassword(password),
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
            Success(
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
                is NetworkException.BadRequestException -> Error(NetworkError.RegisterError.BAD_REQUEST)
                is NetworkException.ConflictException -> Error(NetworkError.RegisterError.CONFLICT)
                is NetworkException.ConnectionException -> Error(NetworkError.RegisterError.CONNECTION)
                else -> {
                    e.printStackTrace()
                    Error(NetworkError.RegisterError.UNKNOWN_ERROR)
                }
            }
        }
    }

    override suspend fun authenticate(coroutineScope: CoroutineScope): Result<User, NetworkError.AuthError> =
        coroutineScope {
            try {
                val accessToken = tokenManager.getAccessToken() ?: return@coroutineScope Result.Error(
                    NetworkError.AuthError.UNKNOWN
                )
                val refreshToken = tokenManager.getRefreshToken() ?: return@coroutineScope Result.Error(
                    NetworkError.AuthError.UNKNOWN
                )
                val res = async { userApi.authenticate(accessToken, refreshToken) }.await()
                Success(
                    User(
                        firstName = res.firstName,
                        lastName = res.lastName,
                        email = res.email,
                        gender = res.gender,
                        dob = res.dob,
                        userName = res.userName,
                        number = res.number,
                    )
                )
            } catch (_: NetworkException) {
                Error(NetworkError.AuthError.UNKNOWN)
            }
        }

    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}