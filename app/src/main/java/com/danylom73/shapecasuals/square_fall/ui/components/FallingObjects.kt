package com.danylom73.shapecasuals.square_fall.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.danylom73.shapecasuals.square_fall.model.FallingObject
import com.danylom73.shapecasuals.square_fall.model.ObjectType

fun DrawScope.drawFallingObjects(
    objects: List<FallingObject>,
    playerX: Float,
    playerY: Float,
    onPoint: () -> Unit,
    onEnemy: () -> Unit,
    enemyColor: Color,
    pointColor: Color
): CollisionResult {
    val removed = mutableListOf<FallingObject>()

    objects.forEach { obj ->
        val color = if (obj.type == ObjectType.POINT) pointColor else enemyColor

        drawRect(
            color = color,
            topLeft = Offset(obj.x, obj.y),
            size = Size(obj.size, obj.size)
        )

        val intersects =
            obj.y + obj.size >= playerY - 25 &&
                    obj.y <= playerY + 25 &&
                    obj.x + obj.size >= playerX - 25 &&
                    obj.x <= playerX + 25

        if (intersects) {
            if (obj.type == ObjectType.POINT) {
                onPoint()
            } else {
                onEnemy()
            }
            removed.add(obj)
        }

        if (obj.y > size.height + 100) {
            removed.add(obj)
        }
    }

    return CollisionResult(removed)
}

data class CollisionResult(val removed: List<FallingObject>)
