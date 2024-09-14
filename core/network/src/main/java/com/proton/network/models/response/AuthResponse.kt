package com.proton.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val age: Int,
    val password: String,
    val gender: String,
    val dob: String = "",
    var userName: String = firstName+lastName,
    val number: Long = 0,
    val cardId: List<Int> = listOf(),
    val address: String = ""
)