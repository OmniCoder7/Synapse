package com.example.compose
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.ui.theme.AppTypography
import com.proton.synapse.ui.theme.backgroundDark
import com.proton.synapse.ui.theme.backgroundLight
import com.proton.synapse.ui.theme.errorContainerDark
import com.proton.synapse.ui.theme.errorContainerLight
import com.proton.synapse.ui.theme.errorDark
import com.proton.synapse.ui.theme.errorLight
import com.proton.synapse.ui.theme.inverseOnSurfaceDark
import com.proton.synapse.ui.theme.inverseOnSurfaceLight
import com.proton.synapse.ui.theme.inversePrimaryDark
import com.proton.synapse.ui.theme.inversePrimaryLight
import com.proton.synapse.ui.theme.inverseSurfaceDark
import com.proton.synapse.ui.theme.inverseSurfaceLight
import com.proton.synapse.ui.theme.onBackgroundDark
import com.proton.synapse.ui.theme.onBackgroundLight
import com.proton.synapse.ui.theme.onErrorContainerDark
import com.proton.synapse.ui.theme.onErrorContainerLight
import com.proton.synapse.ui.theme.onErrorDark
import com.proton.synapse.ui.theme.onErrorLight
import com.proton.synapse.ui.theme.onPrimaryContainerDark
import com.proton.synapse.ui.theme.onPrimaryContainerLight
import com.proton.synapse.ui.theme.onPrimaryDark
import com.proton.synapse.ui.theme.onPrimaryLight
import com.proton.synapse.ui.theme.onSecondaryContainerDark
import com.proton.synapse.ui.theme.onSecondaryContainerLight
import com.proton.synapse.ui.theme.onSecondaryDark
import com.proton.synapse.ui.theme.onSecondaryLight
import com.proton.synapse.ui.theme.onSurfaceDark
import com.proton.synapse.ui.theme.onSurfaceLight
import com.proton.synapse.ui.theme.onSurfaceVariantDark
import com.proton.synapse.ui.theme.onSurfaceVariantLight
import com.proton.synapse.ui.theme.onTertiaryContainerDark
import com.proton.synapse.ui.theme.onTertiaryContainerLight
import com.proton.synapse.ui.theme.onTertiaryDark
import com.proton.synapse.ui.theme.onTertiaryLight
import com.proton.synapse.ui.theme.outlineDark
import com.proton.synapse.ui.theme.outlineLight
import com.proton.synapse.ui.theme.outlineVariantDark
import com.proton.synapse.ui.theme.outlineVariantLight
import com.proton.synapse.ui.theme.primaryContainerDark
import com.proton.synapse.ui.theme.primaryContainerLight
import com.proton.synapse.ui.theme.primaryDark
import com.proton.synapse.ui.theme.primaryLight
import com.proton.synapse.ui.theme.scrimDark
import com.proton.synapse.ui.theme.scrimLight
import com.proton.synapse.ui.theme.secondaryContainerDark
import com.proton.synapse.ui.theme.secondaryContainerLight
import com.proton.synapse.ui.theme.secondaryDark
import com.proton.synapse.ui.theme.secondaryLight
import com.proton.synapse.ui.theme.surfaceBrightDark
import com.proton.synapse.ui.theme.surfaceBrightLight
import com.proton.synapse.ui.theme.surfaceContainerDark
import com.proton.synapse.ui.theme.surfaceContainerHighDark
import com.proton.synapse.ui.theme.surfaceContainerHighLight
import com.proton.synapse.ui.theme.surfaceContainerHighestDark
import com.proton.synapse.ui.theme.surfaceContainerHighestLight
import com.proton.synapse.ui.theme.surfaceContainerLight
import com.proton.synapse.ui.theme.surfaceContainerLowDark
import com.proton.synapse.ui.theme.surfaceContainerLowLight
import com.proton.synapse.ui.theme.surfaceContainerLowestDark
import com.proton.synapse.ui.theme.surfaceContainerLowestLight
import com.proton.synapse.ui.theme.surfaceDark
import com.proton.synapse.ui.theme.surfaceDimDark
import com.proton.synapse.ui.theme.surfaceDimLight
import com.proton.synapse.ui.theme.surfaceLight
import com.proton.synapse.ui.theme.surfaceVariantDark
import com.proton.synapse.ui.theme.surfaceVariantLight
import com.proton.synapse.ui.theme.tertiaryContainerDark
import com.proton.synapse.ui.theme.tertiaryContainerLight
import com.proton.synapse.ui.theme.tertiaryDark
import com.proton.synapse.ui.theme.tertiaryLight

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun SynapseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

