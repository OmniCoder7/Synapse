package com.proton.network.utils

object ApiEndpoints {
    const val REGISTER = "/auth/register"
    const val LOGIN = "/auth/login"
    const val CURRENT_USER_TOKEN = "/auth/token"
    const val GET_USER = "/auth/"

    const val PRODUCT_PREVIEW = "/product/productPreviews"
    fun cart(userId: Long) = "/$userId/cart"
    fun wishList(userId: Long) = "/$userId/wishList"
}