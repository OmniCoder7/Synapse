package com.proton.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proton.data.local.database.productPreview.ProductPreviewDao
import com.proton.data.local.database.productPreview.ProductPreviewEntity
import com.proton.data.local.database.remoteKey.RemoteKey
import com.proton.data.local.database.remoteKey.RemoteKeyDao

@Database(entities = [ProductPreviewEntity::class, RemoteKey::class], version = 1)
abstract class ProductPreviewDatabase : RoomDatabase() {
    abstract fun productPreviewDao(): ProductPreviewDao
    abstract fun remoteKey(): RemoteKeyDao

    var lastUpdate = 0L

    companion object {
        private const val DATABASE_NAME = "product_preview_database"

        @Volatile
        private var instance: ProductPreviewDatabase? = null

        fun getInstance(context: Context): ProductPreviewDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProductPreviewDatabase {
            return Room.databaseBuilder(context, ProductPreviewDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}