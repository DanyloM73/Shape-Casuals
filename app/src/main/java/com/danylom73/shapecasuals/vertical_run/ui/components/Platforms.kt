package com.danylom73.shapecasuals.vertical_run.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.vertical_run.model.Platform

fun DrawScope.drawPlatforms(
    objects: List<Platform>,
    playerX: Float,
    playerY: Float,
    onPassed: () -> Unit,
    onCollision: () -> Unit,
    color: Color
): CollisionResult {

    val removed = mutableListOf<Platform>()

    objects.forEach { obj ->
        drawRect(
            color = color,
            topLeft = Offset(obj.x, obj.y),
            size = Size(obj.width, obj.height)
        )

        val playerRect = Rect(
            left = playerX - 60,
            top = playerY - 60,
            right = playerX + 60,
            bottom = playerY + 60
        )

        val objRect = Rect(
            left = obj.x,
            top = obj.y,
            right = obj.x + obj.width,
            bottom = obj.y + obj.height
        )

        val intersects =
            objRect.left < playerRect.right &&
                    objRect.right > playerRect.left &&
                    objRect.top < playerRect.bottom &&
                    objRect.bottom > playerRect.top

        if (intersects) {
            onCollision()
            removed.add(obj)
        }

        if (!obj.passed && obj.y > playerY + 80) {
            obj.passed = true
            onPassed()
        }

        if (obj.y > size.height + 200) {
            removed.add(obj)
        }
    }

    return CollisionResult(removed)
}

data class CollisionResult(val removed: List<Platform>)
