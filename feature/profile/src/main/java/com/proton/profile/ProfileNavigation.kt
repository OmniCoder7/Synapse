package com.proton.profile

import ProfileScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Profile

fun NavHostController.profile() {
    navigate(Profile)
}

fun NavGraphBuilder.profile() {
    composable<Profile> {
        ProfileScreen()
    }
}