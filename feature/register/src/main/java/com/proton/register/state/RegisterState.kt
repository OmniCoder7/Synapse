package com.proton.register.state

data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val userName: String = "",
    val gender: String = "Male",
    val dob: Long = 0
)
