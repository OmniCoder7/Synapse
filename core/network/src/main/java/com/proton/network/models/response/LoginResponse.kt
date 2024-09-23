package com.proton.network.models.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginResponse(
    var accessToken: String="",
    var refreshToken: String="",
    val userId: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val dob: String = "",
    val age: Int = 0,
    var userName: String = "",
    val number: Long = 0,
)
