package com.danylom73.shapecasuals.vertical_run.model

data class Platform(
    var x: Float,
    var y: Float,
    val width: Float,
    val height: Float,
    val speed: Float = 15f,
    var passed: Boolean = false
)