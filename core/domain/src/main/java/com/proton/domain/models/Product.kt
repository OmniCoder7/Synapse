package com.proton.domain.models

data class Product(
    val productId: Long,
    val productName: String,
    val price: Int,
    val rating: Double,
    val discount: Int,
    val reviews: List<Long>,
    val stockQuantity: Int,
    val imageUrl: String? = null,
    val weight: Double? = null,
    val dimensions: Dimensions? = null,
    val tags: List<String> = emptyList(),
)

data class Dimensions(
    val length: Double,
    val width: Double,
    val height: Double
)

fun Product.toProductPreview() =
    ProductPreview(
        productId = productId,
        productName = productName,
        price = price,
        rating = rating,
        discount = discount,
        imageUrl = imageUrl
    )