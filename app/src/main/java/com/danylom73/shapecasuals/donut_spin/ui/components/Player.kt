package com.danylom73.shapecasuals.donut_spin.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.donut_spin.model.Player
import com.danylom73.shapecasuals.donut_spin.util.getCoordinates

fun DrawScope.drawPlayer(center: Offset, player: Player, color: Color) {
    val playerCords = getCoordinates(
        center.x,
        center.y,
        player.angle
    )

    drawCircle(
        color = color,
        radius = player.radius,
        center = Offset(playerCords.first, playerCords.second)
    )
}