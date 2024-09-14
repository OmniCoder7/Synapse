package com.proton.register

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Register

fun NavHostController.register() {
    navigate(Register)
}

fun NavGraphBuilder.register(
    modifier: Modifier = Modifier,
    toLogin: () -> Unit,
    toHome: (Long) -> Unit
) {
    composable<Register> {
        RegisterScreen(
            modifier = modifier,
            toLogin = toLogin,
            toHome = toHome
        )
    }

}