package com.danylom73.shapecasuals.donut_spin.util

import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun randomAngle(): Float = Random.nextFloat() * 360f

fun getCoordinates(
    centerX: Float,
    centerY: Float,
    angle: Float
): Pair<Float, Float> {
    val x = centerX + (DONUT_RADIUS - DONUT_THICKNESS / 2) *
            cos(Math.toRadians(angle.toDouble())).toFloat()
    val y = centerY + (DONUT_RADIUS - DONUT_THICKNESS / 2) *
            sin(Math.toRadians(angle.toDouble())).toFloat()

    return x to y
}
