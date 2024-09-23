package com.proton.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.proton.data.local.database.ProductPreviewDatabase
import com.proton.data.local.database.productPreview.toProductPreview
import com.proton.domain.models.ProductPreview
import com.proton.domain.service.ProductService
import com.proton.network.api.ProductApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val api: ProductApi,
    private val database: ProductPreviewDatabase,
) : ProductService {

    override fun getProduct(queryString: String): Flow<PagingData<ProductPreview>> {
        val pagingSourceFactory = { database.productPreviewDao().productPreviewByName(queryString) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ProductPreviewRemoteMediator(
                productApi = api,
                database = database,
                query = queryString
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toProductPreview() }
        }
    }

    override suspend fun addToWishList(productId: Long) =
        api.addToWishList(productId)


    override suspend fun addToCart(productId: Long) = api.addToCart(productId)

    override suspend fun removeFromWishList(productId: Long) = api.removeFromWishList(productId)

    override suspend fun removeFromCart(productId: Long) = api.removeFromCart(productId)

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}