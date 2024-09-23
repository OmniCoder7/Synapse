package com.proton.domain.service

import androidx.paging.PagingData
import com.proton.domain.error.NetworkError
import com.proton.domain.models.Product
import com.proton.domain.models.ProductPreview
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ProductService {
    fun getProduct(queryString: String): Flow<PagingData<ProductPreview>>
    suspend fun addToWishList(productId: Long): Boolean
    suspend fun addToCart(productId: Long): Boolean
    suspend fun removeFromWishList(productId: Long): Boolean
    suspend fun removeFromCart(productId: Long): Boolean
}