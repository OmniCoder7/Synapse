package com.proton.synapse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.compose.SynapseTheme
import com.proton.synapse.navigation.Authentication
import com.proton.synapse.navigation.Main
import com.proton.synapse.navigation.SynapseNavigation
import com.proton.synapse.ui.composable.SynapseBottomAppBar
import com.proton.synapse.ui.composable.SynapseTopAppBar
import org.koin.androidx.compose.koinViewModel

class SynapseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SynapseTheme {
                val viewModel: SynapseViewModel = koinViewModel()
                val authState by viewModel.authState.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { SynapseTopAppBar(navController = navController) },
                    bottomBar = {
                        if (authState is AuthState.Authenticated)
                            SynapseBottomAppBar(navController = navController)
                    }
                ) { innerPadding ->

                    installSplashScreen().setKeepOnScreenCondition {
                        when (authState) {
                            is AuthState.Loading -> true
                            is AuthState.Authenticated -> false
                            is AuthState.Unauthenticated -> false
                        }
                    }

                    val startDestination: Any = when (authState) {
                        AuthState.Loading -> Authentication
                        AuthState.Unauthenticated -> Authentication
                        is AuthState.Authenticated -> Main
                    }
                    SynapseNavigation(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal,
                                ),
                            ), startDestination = startDestination, navController = navController
                    )
                }
            }
        }
    }
}

sealed class Screen(val route: Any, @DrawableRes val resourceId: Int, val title: String) {
    data object Home :
        Screen(title = "Home", resourceId = R.drawable.home, route = com.proton.home.Home)

    data object Profile : Screen(
        title = "Profile", resourceId = R.drawable.profile, route = com.proton.profile.Profile
    )

}

internal val screens = listOf(
    Screen.Home,
    Screen.Profile,
)