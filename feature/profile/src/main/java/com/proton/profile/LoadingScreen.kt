package com.proton.profile

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val width = LocalConfiguration.current.screenWidthDp
        val height = LocalConfiguration.current.screenHeightDp
        ProgressIndicator(center = Offset(width / 2f, height / 2f))
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    center: Offset,
) {
    val lineAngle by rememberInfiniteTransition(label = "")
        .animateFloat(
            initialValue = 0f,
            targetValue = 2 * Math.PI.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "circular progress indicator"
        )
    val lineRadius = 90f
    Canvas(modifier = modifier) {

        drawCircle(
            center = Offset(size.minDimension / 2, size.minDimension / 2),
            radius = 15f,
            color = Color(0xE8E74C3C)
        )

        drawCircle(
            center = Offset(size.minDimension / 2, size.minDimension / 2),
            radius = 20f + 20f,
            color = Color(0x5934495E),
            style = Stroke(width = 10f)
        )

        drawCircle(
            center = Offset(size.width / 2, size.height / 2),
            radius = 20f + 40f,
            color = Color(0x5934495E),
            style = Stroke(width = 10f)
        )

        drawLine(
            color = Color(0xFFE74C3C),
            start = Offset(size.minDimension / 2, size.minDimension / 2),
            end = Offset(lineRadius * cos(lineAngle), lineRadius * sin(lineAngle)),
            cap = StrokeCap.Round,
            strokeWidth = 8f
        )

        drawCircle(
            center = Offset(size.minDimension / 2 + (lineRadius + 5f), size.minDimension / 2),
            radius = 15f,
            color = Color(0xFF626AAD)
        )

        drawCircle(
            center = Offset(size.minDimension / 2 - (lineRadius + 5f), size.minDimension / 2),
            radius = 15f,
            color = Color(0xE82235F1)
        )

        drawCircle(
            center = Offset(size.minDimension / 2, size.minDimension / 2 + (lineRadius + 5f)),
            radius = 15f,
            color = Color(0xE84A56C2)
        )

        drawCircle(
            center = Offset(size.minDimension / 2, size.minDimension / 2 - (lineRadius + 5f)),
            radius = 15f,
            color = Color(0xE80018FF)
        )
    }
}