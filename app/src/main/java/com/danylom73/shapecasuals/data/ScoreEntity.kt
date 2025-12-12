package com.danylom73.shapecasuals.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey
    val gameName: String,
    val bestScore: Int
)