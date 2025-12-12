package com.danylom73.shapecasuals.donut_spin.logic

import com.danylom73.shapecasuals.donut_spin.model.State
import com.danylom73.shapecasuals.donut_spin.util.DESTROYER_COORDINATE
import com.danylom73.shapecasuals.donut_spin.util.ENEMY_SPEED
import com.danylom73.shapecasuals.donut_spin.util.PLAYER_SPEED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun runGameLoop(
    state: State,
    isActive: Boolean,
    onTick: () -> Unit
) {
    while (isActive) {
        state.angle += PLAYER_SPEED * state.direction
        if (state.angle > 360f) state.angle -= 360f
        if (state.angle < 0f) state.angle += 360f

        withContext(Dispatchers.Main) {
            state.enemies.forEach { it.x += ENEMY_SPEED }
            state.enemies.removeAll { it.x > DESTROYER_COORDINATE }
        }

        state.pointAngle = (state.pointAngle + 2f) % 360f

        onTick()
        delay(16L)
    }
}
