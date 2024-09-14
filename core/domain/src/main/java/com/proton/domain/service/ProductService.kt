package com.proton.domain.service

import com.proton.domain.error.NetworkError
import com.proton.domain.models.ProductPreview
import com.proton.domain.util.Result
import kotlinx.coroutines.CoroutineScope

interface ProductService {
    fun getPreviewProducts(
        pageSize: Int,
        pageNo: Int,
        coroutineScope: CoroutineScope,
    ): Result<List<ProductPreview>, NetworkError.ProductError>
}