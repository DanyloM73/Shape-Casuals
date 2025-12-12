package com.danylom73.shapecasuals.donut_spin.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class State(
    var angle: Float = 0f,
    var pointAngle: Float = 0f,
    var direction: Float = 1f,
    val enemies: SnapshotStateList<Enemy> = mutableStateListOf(),
    val points: MutableList<Point> = mutableListOf(Point())
)
