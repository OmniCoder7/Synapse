package com.proton.network.repositories

import com.proton.network.api.ProductApi
import com.proton.network.models.response.ProductPreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async

class ProductRepositoryImpl(
    private val productApi: ProductApi,
) : ProductRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPreviewProducts(
        pageSize: Int,
        pageNo: Int,
        coroutineScope: CoroutineScope,
    ): List<ProductPreview> {
        val res =
            coroutineScope.async { productApi.getProducts(pageSize = pageSize, pageNo = pageNo) }
        return res.getCompleted()
    }
}