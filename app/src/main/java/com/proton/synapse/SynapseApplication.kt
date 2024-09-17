package com.proton.synapse

import android.app.Application
import coil.Coil
import coil.ImageLoader
import com.proton.data.di.dataModule
import com.proton.data.remote.AuthServiceImpl
import com.proton.data.remote.ProductServiceImpl
import com.proton.data.remote.UserServiceImpl
import com.proton.domain.service.AuthService
import com.proton.domain.service.ProductService
import com.proton.domain.service.UserService
import com.proton.domain.useCase.GetProductPreviewsUseCase
import com.proton.domain.useCase.GetUserUseCase
import com.proton.domain.useCase.LoginUseCase
import com.proton.domain.useCase.RegisterUseCase
import com.proton.domain.util.NetworkConfigure
import com.proton.home.HomeViewModel
import com.proton.login.LoginViewModel
import com.proton.network.di.networkModule
import com.proton.profile.ProfileViewModel
import com.proton.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

class SynapseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SynapseApplication)
            modules(
                viewModelModule,
                useCaseModule,
                dataModule,
                networkModule,
                serviceModule,
                domain
            )
        }

        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .crossfade(true)
                .build()
        )
    }
}

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SynapseViewModel)
    viewModelOf(::RegisterViewModel)
    viewModel { ProfileViewModel(getUserUseCase = get(), id = it.get<Long>()) }
    viewModel {
        HomeViewModel(
            getProductPreviewsUseCase = get(),
        )
    }
}

val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::GetProductPreviewsUseCase)
    factoryOf(::RegisterUseCase)
}

val serviceModule = module {
    factoryOf(::AuthServiceImpl) bind AuthService::class
    factoryOf(::UserServiceImpl) bind UserService::class
    factoryOf(::ProductServiceImpl) bind ProductService::class
}

val domain = module {
    factory { NetworkConfigure(context = androidContext()) }
}
