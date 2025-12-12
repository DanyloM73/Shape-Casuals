package com.danylom73.shapecasuals.donut_spin.ui.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.donut_spin.model.Enemy
import com.danylom73.shapecasuals.donut_spin.model.Player
import com.danylom73.shapecasuals.donut_spin.util.ENEMY_HEIGHT
import com.danylom73.shapecasuals.donut_spin.util.getCoordinates

fun DrawScope.drawEnemies(
    center: Offset,
    enemies: List<Enemy>,
    player: Player,
    color: Color
): Boolean {
    var collision = false

    val playerCords = getCoordinates(
        center.x,
        center.y,
        player.angle
    )

    enemies.forEach {
        drawRoundRect(
            color = color,
            topLeft = Offset(it.x, center.y + it.y),
            size = Size(it.width, it.height),
            cornerRadius = CornerRadius(
                ENEMY_HEIGHT, ENEMY_HEIGHT
            )
        )

        if (
            Rect(
                it.x, center.y + it.y,
                it.x + it.width, center.y + it.y + it.height
            ).contains(Offset(playerCords.first, playerCords.second))
        ) {
            collision = true
        }
    }

    return collision
}