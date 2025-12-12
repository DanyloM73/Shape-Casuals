package com.danylom73.shapecasuals.domain

import com.danylom73.shapecasuals.data.ScoreDao
import com.danylom73.shapecasuals.data.ScoreEntity

suspend fun updateBestScore(
    dao: ScoreDao,
    gameName: String,
    newScore: Int
) {
    val existing = dao.getScoreForGame(gameName)

    if (existing == null || newScore > existing.bestScore) {
        dao.insertScore(ScoreEntity(gameName, newScore))
    }
}
