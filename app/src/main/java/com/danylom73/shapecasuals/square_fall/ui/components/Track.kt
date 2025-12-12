package com.danylom73.shapecasuals.square_fall.ui.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.square_fall.util.TRACK_PADDING

fun DrawScope.drawTrackBackground(size: Size, y: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(TRACK_PADDING, y - 80f),
        size = Size(size.width - 2 * TRACK_PADDING, 160f),
        cornerRadius = CornerRadius(80f, 80f)
    )
}