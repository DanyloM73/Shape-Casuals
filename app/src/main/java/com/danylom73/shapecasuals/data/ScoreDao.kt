package com.danylom73.shapecasuals.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT * FROM scores")
    suspend fun getAllScores(): List<ScoreEntity>

    @Query("SELECT * FROM scores WHERE gameName = :game")
    suspend fun getScoreForGame(game: String): ScoreEntity?
}