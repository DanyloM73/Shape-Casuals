package com.danylom73.shapecasuals.square_fall.model

data class FallingObject(
    var x: Float,
    var y: Float,
    val size: Float,
    val type: ObjectType,
    val speed: Float
)

enum class ObjectType { ENEMY, POINT }
