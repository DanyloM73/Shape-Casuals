package com.danylom73.shapecasuals.square_fall.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawPlayer(x: Float, y: Float, radius: Float, color: Color) {
    drawCircle(color, radius = radius, center = Offset(x, y))
}