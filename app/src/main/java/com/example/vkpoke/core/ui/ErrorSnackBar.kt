package com.example.vkpoke.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackBar(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit
){
    val density = LocalDensity.current

    val translationY = remember {
        Animatable(100f)
    }

    LaunchedEffect(key1 = true){
        translationY.animateTo(0f)
    }

    Row(
        modifier = modifier
            .graphicsLayer {
                this.translationY = with(density) { translationY.value.dp.toPx() }
            }
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            modifier = Modifier.weight(1f),
            text = message,
            overflow = TextOverflow.Ellipsis,
        )

        IconButton(onClick = onRetry) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "retry load")
        }
    }
}