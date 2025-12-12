package com.danylom73.shapecasuals.vertical_run.model

data class Player(
    var x: Float = 0f,
    var direction: Float = 1f,
    val speed: Float = 14f,
    var leftBound: Float = 0f,
    var rightBound: Float = 0f
)
