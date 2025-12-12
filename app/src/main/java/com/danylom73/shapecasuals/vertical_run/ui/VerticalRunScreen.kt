package com.danylom73.shapecasuals.vertical_run.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danylom73.shapecasuals.R
import com.danylom73.shapecasuals.data.AppDatabase
import com.danylom73.shapecasuals.domain.updateBestScore
import com.danylom73.shapecasuals.ui.navigation.Screen
import com.danylom73.shapecasuals.ui.theme.AppFontFamily
import com.danylom73.shapecasuals.ui.theme.lighterBrown
import com.danylom73.shapecasuals.ui.theme.orange
import com.danylom73.shapecasuals.ui.theme.black
import com.danylom73.shapecasuals.vertical_run.logic.runGameLoop
import com.danylom73.shapecasuals.vertical_run.logic.spawnPlatforms
import com.danylom73.shapecasuals.vertical_run.model.Player
import com.danylom73.shapecasuals.vertical_run.model.GameState
import com.danylom73.shapecasuals.vertical_run.ui.components.drawPlatforms
import com.danylom73.shapecasuals.vertical_run.ui.components.drawPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun VerticalRunScreen(
    playerColor: Color = black,
    platformColor: Color = lighterBrown,
    backgroundColor: Color = orange,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scoreDao = remember { db.scoreDao() }

    val player = remember { Player(x = 0f, direction = 1f) }
    val gameState = remember { GameState() }

    var isPlaying by remember { mutableStateOf(true) }
    var score by remember { mutableIntStateOf(0) }

    val snapshot = remember { mutableStateOf(gameState.copy()) }

    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            updateBestScore(scoreDao, Screen.VerticalRunGame.route, score)
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            launch(Dispatchers.Main) {
                spawnPlatforms(
                    list = gameState.platforms,
                    playing = isPlaying,
                    borders = Pair(
                        (player.leftBound - 50f).toInt(),
                        (player.rightBound + 50f).toInt()
                    )
                )
            }
            launch(Dispatchers.Main) {
                runGameLoop(gameState, player) {
                    snapshot.value = gameState.copy(
                        platforms = gameState.platforms
                    )
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures {
                    player.direction *= -1f
                }
            }
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = platformColor,
            modifier = Modifier
                .padding(start = 20.dp, top = 50.dp)
                .size(50.dp)
                .clickable { onGoHome() }
        )

        Text(
            text = score.toString(),
            modifier = Modifier.fillMaxWidth().padding(top = 100.dp),
            textAlign = TextAlign.Center,
            color = platformColor,
            fontSize = 50.sp,
            fontFamily = AppFontFamily,
            fontWeight = FontWeight.Bold
        )

        Canvas(Modifier.fillMaxSize()) {
            player.leftBound = 50f
            player.rightBound = size.width - 50f

            val playerY = size.height * 0.75f

            val snap = snapshot.value

            drawPlayer(
                x = snap.playerX,
                y = playerY,
                width = 120f,
                height = 120f,
                color = playerColor
            )

            val result = drawPlatforms(
                objects = snap.platforms,
                playerX = snap.playerX,
                playerY = playerY,
                onPassed = { score++ },
                onCollision = { isPlaying = false },
                color = platformColor
            )

            if (result.removed.isNotEmpty()) {
                gameState.platforms.removeAll(result.removed)
            }
        }

        if (!isPlaying) {
            Button(
                onClick = {
                    gameState.platforms.clear()
                    score = 0
                    isPlaying = true
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .size(80.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = platformColor
                )
            ) {
                Icon(
                    painterResource(R.drawable.ic_restart),
                    contentDescription = null,
                    tint = backgroundColor
                )
            }
        }
    }
}

