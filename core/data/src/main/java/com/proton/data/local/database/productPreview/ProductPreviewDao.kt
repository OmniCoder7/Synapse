package com.proton.data.local.database.productPreview

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductPreviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ProductPreviewEntity>)

    @Query("SELECT * FROM product_preview WHERE productName LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, ProductPreviewEntity>

    @Query("DELETE FROM product_preview")
    suspend fun clearAll()

    @Query("DELETE FROM product_preview WHERE productName LIKE :query")
    suspend fun deleteByQuery(query: String)

    @Query(
        "SELECT * FROM product_preview WHERE " +
                "productName LIKE :queryString ORDER BY price ASC, productName ASC"
    )
    fun productPreviewByName(queryString: String): PagingSource<Int, ProductPreviewEntity>

}