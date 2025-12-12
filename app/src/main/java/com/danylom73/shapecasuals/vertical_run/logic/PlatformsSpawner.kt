package com.danylom73.shapecasuals.vertical_run.logic

import com.danylom73.shapecasuals.vertical_run.model.Platform
import kotlinx.coroutines.delay

suspend fun spawnPlatforms(
    list: MutableList<Platform>,
    playing: Boolean,
    borders: Pair<Int, Int>
) {
    val (leftBound, rightBound) = borders

    while (playing) {
        delay((700..1300).random().toLong())

        val maxWidth = (rightBound - leftBound) / 2
        val width = (200..maxWidth).random().toFloat()

        val stickToLeft = (0..1).random() == 0

        val x = if (stickToLeft) {
            leftBound.toFloat()
        } else {
            (rightBound - width)
        }

        list.add(
            Platform(
                x = x,
                y = 0f,
                width = width,
                height = 60f
            )
        )
    }
}


