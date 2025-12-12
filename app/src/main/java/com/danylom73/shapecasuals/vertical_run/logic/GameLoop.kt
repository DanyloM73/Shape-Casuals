package com.danylom73.shapecasuals.vertical_run.logic

import com.danylom73.shapecasuals.vertical_run.model.Player
import com.danylom73.shapecasuals.vertical_run.model.GameState
import kotlinx.coroutines.delay

suspend fun runGameLoop(
    state: GameState,
    player: Player,
    update: () -> Unit
) {
    while (true) {
        delay(16L)

        player.x += player.speed * player.direction

        if (player.x <= player.leftBound) {
            player.x = player.leftBound
            player.direction *= -1
        }
        if (player.x >= player.rightBound) {
            player.x = player.rightBound
            player.direction *= -1
        }

        state.playerX = player.x

        state.platforms.forEach { it.y += it.speed }

        update()
    }
}

