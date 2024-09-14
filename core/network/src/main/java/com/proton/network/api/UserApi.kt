package com.proton.network.api

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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(private val client: HttpClient) {

    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            client.post(ApiEndpoints.LOGIN) {
                setBody(LoginRequest(email, password))
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            throw mapKtorExceptionToNetworkException(e)
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
        return client.post(ApiEndpoints.REGISTER) {
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
        }.body()
    }

    suspend fun authenticate(accessToken: String): AuthResponse {
        return try {
            client.post(ApiEndpoints.CURRENT_USER_TOKEN) {
                setBody(accessToken)
            }.body()
        } catch (e: NetworkException) {
            throw mapKtorExceptionToNetworkException(e)
        }
    }

    suspend fun authenticate(id: Long): AuthResponse {
        return try {
            client.post(ApiEndpoints.CURRENT_USER_TOKEN) {
                setBody(id)
                contentType(ContentType.Text.Plain)
            }.body()
        } catch (e: NetworkException) {
            throw mapKtorExceptionToNetworkException(e)
        }
    }

    suspend fun getUser(id: Long): User {
        return client.get(ApiEndpoints.GET_USER) {
            setBody(id)
            contentType(ContentType.Text.Plain)
        }.body()
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