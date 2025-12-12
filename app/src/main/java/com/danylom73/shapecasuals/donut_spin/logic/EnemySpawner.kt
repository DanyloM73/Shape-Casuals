package com.danylom73.shapecasuals.donut_spin.logic

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.danylom73.shapecasuals.donut_spin.model.Enemy
import com.danylom73.shapecasuals.donut_spin.util.DONUT_RADIUS
import com.danylom73.shapecasuals.donut_spin.util.ENEMY_HEIGHT
import com.danylom73.shapecasuals.donut_spin.util.ENEMY_WIDTH_MAX
import com.danylom73.shapecasuals.donut_spin.util.ENEMY_WIDTH_MIN
import com.danylom73.shapecasuals.donut_spin.util.SPAWNER_COORDINATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

suspend fun spawnEnemies(
    enemies: SnapshotStateList<Enemy>,
    isActive: Boolean
) {
    while (isActive) {
        val y = Random.nextInt(
            (-DONUT_RADIUS).toInt(),
            (DONUT_RADIUS - ENEMY_HEIGHT).toInt()
        ).toFloat()

        val newEnemy = Enemy(
            x = SPAWNER_COORDINATE,
            y = y,
            width = Random.nextInt(
                ENEMY_WIDTH_MIN, ENEMY_WIDTH_MAX
            ).toFloat(),
            height = ENEMY_HEIGHT
        )

        withContext(Dispatchers.Main) {
            enemies.add(newEnemy)
        }

        delay(1000L)
    }
}
