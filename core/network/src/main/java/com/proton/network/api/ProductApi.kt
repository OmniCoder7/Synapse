package com.proton.network.api

import com.proton.network.models.response.ProductPreview
import com.proton.network.utils.ApiEndpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

class ProductApi(
    private val client: HttpClient,
) {
    suspend fun getProducts(
        pageSize: Int = 20,
        pageNo: Int,
        query: String,
    ): List<ProductPreview> {
        return client.get(ApiEndpoints.PRODUCT_PREVIEW) {
            parameter("pageSize", pageSize)
            parameter("pageNo", pageNo)
            parameter("query", query)
        }.body()
    }

    suspend fun addToCart(productId: Long): Boolean {
        val res = client.post(ApiEndpoints.cart(productId))
        return when (res.status) {
            HttpStatusCode.OK -> true
            else -> false
        }
    }

    suspend fun removeFromCart(productId: Long): Boolean {
        val res = client.delete(ApiEndpoints.cart(productId))
        return when (res.status) {
            HttpStatusCode.OK -> true
            else -> false
        }
    }

    suspend fun addToWishList(productId: Long): Boolean {
        val res = client.post(ApiEndpoints.wishList(productId))
        return when (res.status) {
            HttpStatusCode.OK -> true
            else -> false
        }
    }

    suspend fun removeFromWishList(productId: Long): Boolean {
        val res = client.delete(ApiEndpoints.wishList(productId))
        return when (res.status) {
            HttpStatusCode.OK -> true
            else -> false
        }
    }
}