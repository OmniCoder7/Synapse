package com.proton.network.models.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AuthResponse(
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var userId: Long,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val dob: String = "",
    var userName: String = "",
    val number: Long = 0,
    val loginPassword: String = "",
    var cartProducts: List<Long> = emptyList(),
    var orderIds: List<Long> = emptyList(),
    var wishListIds: List<Long> = emptyList()
)