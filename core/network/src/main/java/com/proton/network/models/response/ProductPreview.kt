package com.proton.network.models.response

data class ProductPreview(
    val productId: Long,
    val productName: String,
    val price: Int,
    val rating: Double,
    val discount: Int,
    val imageUrl: String? = null,
)