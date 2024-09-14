package com.proton.network.api

import com.proton.network.models.response.ProductPreview
import com.proton.network.utils.ApiEndpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApi(
    private val client: HttpClient
) {
    suspend fun getProducts(
        pageSize: Int,
        pageNo: Int
    ): List<ProductPreview> {
        return client.get(ApiEndpoints.PRODUCT_PREVIEW) {
            parameter("pageSize", pageSize)
            parameter("pageNo", pageNo)
        }.body()
    }
}