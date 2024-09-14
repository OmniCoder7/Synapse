package com.proton.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val gender: String,
    val dob: String = "",
    var userName: String = firstName+lastName,
    val number: Long = 0,
    val cardId: List<Int> = listOf(),
    val address: String = ""
)