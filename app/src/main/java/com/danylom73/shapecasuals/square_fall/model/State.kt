package com.danylom73.shapecasuals.square_fall.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class GameState(
    val objects: SnapshotStateList<FallingObject> = mutableStateListOf(),
    var playerX: Float = 0f
)