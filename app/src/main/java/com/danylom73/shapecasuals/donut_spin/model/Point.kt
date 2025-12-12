package com.danylom73.shapecasuals.donut_spin.model

import com.danylom73.shapecasuals.donut_spin.util.randomAngle

data class Point(
    var angle: Float = randomAngle(),
    var size: Float = 60f
)