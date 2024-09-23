package com.proton.network.di

import com.proton.network.api.ProductApi
import com.proton.network.api.UserApi
import com.proton.network.client.create
import com.proton.network.repositories.AuthRepository
import com.proton.network.repositories.AuthRepositoryImpl
import com.proton.network.repositories.ProductRepository
import com.proton.network.repositories.ProductRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single { create() }
    singleOf(::UserApi)
    singleOf(::ProductApi)
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
}