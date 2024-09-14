package com.proton.login

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Login

fun NavHostController.login() {
    navigate(Login)
}

fun NavGraphBuilder.login(
    modifier: Modifier = Modifier,
    toRegister: () -> Unit,
    toForgotPassword: () -> Unit,
    toHome: (Long) -> Unit
) {
    composable<Login> {
        LoginScreen(
            modifier = modifier,
            toRegister = toRegister,
            toForgotPassword = toForgotPassword,
            toHome = toHome
        )
    }
}