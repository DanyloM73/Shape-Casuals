package com.danylom73.shapecasuals.vertical_run.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawPlayer(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color
) {
    drawRect(
        color = color,
        topLeft = Offset(x - width / 2, y - height / 2),
        size = Size(width, height)
    )
}
