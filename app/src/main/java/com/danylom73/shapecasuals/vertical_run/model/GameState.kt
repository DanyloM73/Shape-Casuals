package com.danylom73.shapecasuals.vertical_run.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList


data class GameState(
    val platforms: SnapshotStateList<Platform> = mutableStateListOf(),
    var playerX: Float = 0f
)