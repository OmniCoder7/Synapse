package com.proton.network.utils

object ApiEndpoints {
    private const val baseUrl = "http://192.168.186.117:8080"
    const val REGISTER = "${baseUrl}/auth/register"
    const val LOGIN = "${baseUrl}/auth/login"
    const val CURRENT_USER_TOKEN = "${baseUrl}/auth/token"
    const val GET_USER = "${baseUrl}/auth/"
    const val REFRESH_TOKEN = "${baseUrl}/auth/refresh"
    const val RESET_PASSWORD = "${baseUrl}/forgetPassword/resetPassword"
    const val SAVE_PASSWORD = "${baseUrl}/forgetPassword/savePassword"

    const val PRODUCT_PREVIEW = "${baseUrl}/product/productPreviews"
}