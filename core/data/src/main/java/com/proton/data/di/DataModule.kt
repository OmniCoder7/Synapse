package com.proton.data.di

import com.proton.data.dataStore
import com.proton.data.local.database.ProductPreviewDatabase
import com.proton.data.local.datastore.TokenManagerImpl
import com.proton.data.remote.ProductRepositoryImpl
import com.proton.domain.service.ProductService
import com.proton.network.repositories.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().dataStore }
    singleOf(::TokenManagerImpl) bind TokenManager::class
    single { ProductRepositoryImpl(get(), get()) }
    singleOf(::ProductRepositoryImpl) bind ProductService::class
    single { ProductPreviewDatabase.getInstance(androidContext()) }
}