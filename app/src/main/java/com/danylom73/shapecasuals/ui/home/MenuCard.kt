package com.danylom73.shapecasuals.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danylom73.shapecasuals.ui.theme.AppFontFamily
import com.danylom73.shapecasuals.ui.theme.darkerBlue

@Composable
fun MenuCard(
    title: String,
    accentColor: Color,
    bestScore: Int? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed.value) 0.96f else 1f,
        animationSpec = tween(durationMillis = 120)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(20.dp))
            .background(darkerBlue)
            .border(2.dp, accentColor.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = AppFontFamily
            )

            if (!(bestScore == 0 || bestScore == null)) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Best Score: $bestScore",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = AppFontFamily
                )
            }
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(28.dp)
        )
    }
}

