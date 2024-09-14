package com.proton.network.utils

object ApiEndpoints {
    const val REGISTER = "http://192.168.129.117:8080/auth/register"
    const val LOGIN = "http://192.168.129.117:8080/auth/login"
    const val   CURRENT_USER_TOKEN = "http://192.168.129.117:8080/auth/token"
    const val CURRENT_USER_ID = "http://192.168.129.117:8080/auth/id"
    const val REFRESH_TOKEN = "http://192.168.129.117:8080/auth/refresh"
    const val RESET_PASSWORD = "http://192.168.129.117:8080/forgetPassword/resetPassword"
    const val SAVE_PASSWORD = "http://192.168.129.117:8080/forgetPassword/savePassword"
    const val GET_USER = "http://192.168.129.117:8080/auth/id"

    const val PRODUCT_PREVIEW = "/product/productPreviews"
}