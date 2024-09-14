package com.proton.domain.useCase

import com.proton.domain.models.ProductPreview
import com.proton.domain.service.ProductService
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope

class GetProductPreviewsUseCase(
    private val productService: ProductService,
) {
    operator fun invoke(
        pageSize: Int,
        pageNo: Int,
        coroutineScope: CoroutineScope,
    ): List<ProductPreview> {
        return when(val res = productService.getPreviewProducts(
            pageSize = pageSize,
            pageNo = pageNo,
            coroutineScope = coroutineScope
        )) {
            is Result.Error -> throw Exception()
            is Result.Success -> res.data
        }
    }
}