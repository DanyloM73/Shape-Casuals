package com.danylom73.shapecasuals.donut_spin.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.danylom73.shapecasuals.donut_spin.model.Player
import com.danylom73.shapecasuals.donut_spin.model.Point
import com.danylom73.shapecasuals.donut_spin.util.getCoordinates

fun DrawScope.drawPoint(
    center: Offset,
    point: Point,
    player: Player,
    color: Color,
    rotationAngle: Float
): Boolean {
    var collision = false

    val pointCords = getCoordinates(
        center.x,
        center.y,
        point.angle
    )

    val playerCords = getCoordinates(
        center.x,
        center.y,
        player.angle
    )

    val rectCenter = Offset(pointCords.first, pointCords.second)
    val rectSize = Size(point.size, point.size)

    rotate(
        degrees = rotationAngle,
        pivot = rectCenter
    ) {
        drawRect(
            color = color,
            topLeft = rectCenter - Offset(point.size / 2, point.size / 2),
            size = rectSize
        )
    }

    val rect = Rect(
        offset = rectCenter - Offset(point.size / 2, point.size / 2),
        size = rectSize
    )
    if (
        rect.contains(
            Offset(playerCords.first, playerCords.second)
        )
    ) collision = true

    return collision
}
