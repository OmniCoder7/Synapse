package com.proton.register.composable

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun NoTintIcon(modifier: Modifier = Modifier,
               id: Int,
               contentDescription: String? = null) {
    Icon(modifier = modifier, imageVector = ImageVector.vectorResource(id), contentDescription = contentDescription, tint = Color.Unspecified)
}