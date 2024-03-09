package com.dwiki.nodemcu.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dwiki.nodemcu.ui.component.WavesLoadingIndicator
import com.dwiki.nodemcu.ui.theme.BlueTurquoise
import com.dwiki.nodemcu.ui.theme.DarkBlue

@Composable
fun SplashScreen(
    navigateToMonitoring: () -> Unit
) {
    val animatable = remember {Animatable (initialValue = 0f)}
    val waterLevel by remember { derivedStateOf { animatable.value }}

    LaunchedEffect(animatable){
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
        if(waterLevel == 1f) navigateToMonitoring()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(BlueTurquoise)
    ) {
        WavesLoadingIndicator(
            modifier = Modifier.fillMaxSize(),
            color = DarkBlue,
            progress = waterLevel
        )
        Icon(
            imageVector = Icons.Default.WaterDrop,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(60.dp)
        )
    }
}