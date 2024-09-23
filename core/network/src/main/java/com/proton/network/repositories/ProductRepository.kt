package com.proton.network.repositories

import com.proton.network.models.response.ProductPreview
import kotlinx.coroutines.CoroutineScope

interface ProductRepository {
    fun getPreviewProducts(pageSize: Int, pageNo: Int, coroutineScope: CoroutineScope, query: String): List<ProductPreview>
}