package com.proton.synapse.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.proton.domain.models.User
import com.proton.home.Home
import com.proton.home.home
import com.proton.login.Login
import com.proton.login.login
import com.proton.profile.profile
import com.proton.register.register
import kotlinx.serialization.Serializable

@Composable
fun SynapseNavigation(
    modifier: Modifier = Modifier, startDestination: Any,
    navController: NavHostController,
    user: User
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        authNav(navController)
        mainNav(navController,
            user.userId)
    }
}

fun NavGraphBuilder.authNav(
    navController: NavHostController,
) {
    navigation<Authentication>(
        startDestination = Login,
    ) {
        login(
            toRegister = { navController.register() },
            toHome = { navController.navigate(Main) },
            toForgotPassword = {}
        )

        register(
            toLogin = { navController.popBackStack() },
            toHome = { navController.navigate(Main) }
        )
    }
}

fun NavGraphBuilder.mainNav(
    navController: NavHostController,
    userId: Long,
) {
    navigation<Main>(
         startDestination = Home,
     ) {
        home(userId)
        profile()
    }
}

@Serializable
object Authentication

@Serializable
object Main