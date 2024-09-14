package com.proton.synapse.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proton.home.Home
import com.proton.profile.Profile
import com.proton.register.Register

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynapseTopAppBar(modifier: Modifier = Modifier,
                     navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    CenterAlignedTopAppBar(modifier = modifier,
        title = { Text("Synapse") }, navigationIcon = {
        Icon(imageVector = when (currentDestination?.route) {
            Profile.toString().substringBefore("@"),
            Register.toString().substringBefore("@"),
                -> Icons.AutoMirrored.Outlined.KeyboardArrowLeft

            Home.toString().substringBefore("@") -> Icons.Default.Menu
            else -> Icons.Default.Home
        }, contentDescription = null, modifier = Modifier.clickable {
            when (currentDestination?.route) {
                Profile.toString().substringBefore("@"),
                    -> navController.navigateUp()

                else -> Icons.Default.Home
            }
        })
    })

}