package com.danylom73.shapecasuals.square_fall.logic

import com.danylom73.shapecasuals.square_fall.model.FallingObject
import com.danylom73.shapecasuals.square_fall.model.ObjectType
import kotlinx.coroutines.delay

suspend fun spawnFallingObjects(
    list: MutableList<FallingObject>,
    playing: Boolean,
    borders: Pair<Int, Int>
) {
    while (playing) {
        delay(1000)

        val isPoint = (0..2).random() == 0

        list.add(
            FallingObject(
                x = (borders.first..borders.second).random().toFloat(),
                y = 0f,
                size = 100f,
                type = if (isPoint) ObjectType.POINT else ObjectType.ENEMY,
                speed = (8..32).random().toFloat()
            )
        )
    }
}
