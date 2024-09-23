package com.proton.data.remote

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.proton.data.local.database.ProductPreviewDatabase
import com.proton.data.local.database.productPreview.ProductPreviewEntity
import com.proton.data.local.database.remoteKey.RemoteKey
import com.proton.network.api.ProductApi
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val PRODUCT_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ProductPreviewRemoteMediator(
    private val database: ProductPreviewDatabase,
    private val productApi: ProductApi,
    private val query: String
) : RemoteMediator<Int, ProductPreviewEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductPreviewEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PRODUCT_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val apiResponse =
                productApi.getProducts(pageNo = page, pageSize = state.config.pageSize, query = query)

            val repos = apiResponse
            val endOfPaginationReached = repos.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKey().clearRemoteKeys()
                    database.productPreviewDao().clearAll()
                }
                val prevKey = if (page == PRODUCT_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKey(repoId = it.productId, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKey().insertAll(keys)
                database.productPreviewDao().insertAll(repos.map {
                    ProductPreviewEntity(
                        productId = it.productId,
                        productName = it.productName,
                        price = it.price,
                        rating = it.rating,
                        discount = it.discount,
                        imageUrl = it.imageUrl
                    )
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - database.lastUpdate <= cacheTimeout) {
            database.lastUpdate = System.currentTimeMillis()
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductPreviewEntity>,
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.productId?.let { repoId ->
                database.remoteKey().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProductPreviewEntity>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
                database.remoteKey().remoteKeysRepoId(repo.productId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProductPreviewEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
                database.remoteKey().remoteKeysRepoId(repo.productId)
            }
    }

}