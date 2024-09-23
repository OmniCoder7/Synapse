package com.proton.domain.useCase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.proton.domain.models.ProductPreview
import com.proton.domain.service.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetProductPreviewsUseCase(
    private val productService: ProductService,
) {
    operator fun invoke(coroutineScope: CoroutineScope, query: String): Flow<PagingData<ProductPreview>> =
        productService.getProduct(query).cachedIn(coroutineScope)
}