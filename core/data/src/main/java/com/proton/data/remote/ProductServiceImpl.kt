package com.proton.data.remote

import com.proton.domain.error.NetworkError
import com.proton.domain.models.ProductPreview
import com.proton.domain.service.ProductService
import com.proton.domain.util.Result
import com.proton.network.repositories.ProductRepository
import kotlinx.coroutines.CoroutineScope

class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : ProductService {
    override fun getPreviewProducts(
        pageSize: Int,
        pageNo: Int,
        coroutineScope: CoroutineScope,
    ): Result<List<ProductPreview>, NetworkError.ProductError> {
        val res = productRepository.getPreviewProducts(
            pageSize = pageSize,
            pageNo = pageNo,
            coroutineScope = coroutineScope
        )

        return Result.Success(res.map {
            ProductPreview(
                productId = it.productId,
                productName = it.productName,
                price = it.price,
                rating = it.rating,
                discount = it.discount,
                imageUrl = it.imageUrl
            )
        })
    }
}