package com.proton.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object Home

fun NavHostController.home() {
    navigate(Home)
}

fun NavGraphBuilder.home(id: Long) {

    composable<Home> {
        val viewModel = koinViewModel<HomeViewModel> { parametersOf(id) }
        val products by viewModel.products.collectAsStateWithLifecycle()
        HomeScreen(modifier = Modifier.fillMaxSize(),
            products = products,
            addToCart = { viewModel.addToCart(it) },
            addToWishList = {viewModel.addToWishList(it)})
    }
}