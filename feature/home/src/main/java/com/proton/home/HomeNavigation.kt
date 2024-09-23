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

@Serializable
object Home

fun NavHostController.home() {
    navigate(Home)
}

fun NavGraphBuilder.home() {

    composable<Home> {
        val viewModel = koinViewModel<HomeViewModel>()
        HomeScreen(modifier = Modifier.fillMaxSize(),
            addToCart = { viewModel.addToCart(it) },
            addToWishList = {viewModel.addToWishList(it)})
    }
}