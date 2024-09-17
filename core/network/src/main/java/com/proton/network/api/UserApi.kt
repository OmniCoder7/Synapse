package com.proton.network.api

import android.util.Log
import android.util.Log.e
import com.proton.network.exception.NetworkException
import com.proton.network.exception.mapKtorExceptionToNetworkException
import com.proton.network.models.request.LoginRequest
import com.proton.network.models.request.RegisterRequest
import com.proton.network.models.response.AuthResponse
import com.proton.network.models.response.LoginResponse
import com.proton.network.utils.ApiEndpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UserApi(private val client: HttpClient) {

    suspend fun login(email: String, password: String): LoginResponse {
        val res = client.post(ApiEndpoints.LOGIN) {
            setBody(LoginRequest(email, password))
            contentType(ContentType.Application.Json)
        }
        return when (res.status) {
            HttpStatusCode.Unauthorized -> throw NetworkException.UnauthorizedException()
            HttpStatusCode.OK -> res.body()
            else -> {
                e("NetworkError", res.status.toString())
                throw NetworkException.UnknownException()
            }
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        gender: String,
        dob: String,
        userName: String,
        number: Long,
        address: String,
    ): LoginResponse {
        val res = client.post(ApiEndpoints.REGISTER) {
            setBody(
                RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    gender = gender,
                    dob = dob,
                    userName = userName,
                    number = number,
                    address = address
                )
            )
            contentType(ContentType.Application.Json)
        }
        return when (res.status) {
            HttpStatusCode.BadRequest -> throw NetworkException.BadRequestException()
            HttpStatusCode.NotFound -> throw NetworkException.NotFoundException()
            HttpStatusCode.Unauthorized -> throw NetworkException.UnauthorizedException()
            HttpStatusCode.OK -> res.body()
            else -> {
                e("NetworkError", res.status.toString())
                throw NetworkException.UnknownException()
            }
        }
    }

    suspend fun authenticate(accessToken: String): AuthResponse {
        val res = client.get(ApiEndpoints.CURRENT_USER_TOKEN) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
        return when (res.status) {
            HttpStatusCode.BadRequest -> throw NetworkException.BadRequestException()
            HttpStatusCode.OK -> res.body()
            else -> {
                e("NetworkError", res.status.toString())
                throw NetworkException.UnknownException()
            }
        }
    }

    suspend fun getUser(id: Long): User {
        return try {
            client.get(ApiEndpoints.GET_USER + id.toString()).body()
        } catch (e: NetworkException) {
            throw mapKtorExceptionToNetworkException(e)
        }
    }
}

data class User(
    var userId: Long,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val dob: String = "",
    val age: Int = 0,
    var userName: String = "",
    val number: Long = 0,
    val loginPassword: String = "",
    var cartProducts: List<Long> = emptyList(),
    var orderIds: List<Long> = emptyList(),
    var wishListIds: List<Long> = emptyList(),
)