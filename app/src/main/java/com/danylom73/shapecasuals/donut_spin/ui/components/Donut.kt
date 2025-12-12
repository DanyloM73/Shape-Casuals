package com.danylom73.shapecasuals.donut_spin.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.donut_spin.util.DONUT_RADIUS
import com.danylom73.shapecasuals.donut_spin.util.DONUT_THICKNESS

fun DrawScope.drawDonut(
    center: Offset,
    color: Color,
    bgColor: Color
) {
    drawCircle(
        color = color,
        radius = DONUT_RADIUS,
        center = center
    )
    drawCircle(
        color = bgColor,
        radius = DONUT_RADIUS - DONUT_THICKNESS,
        center = center
    )
}