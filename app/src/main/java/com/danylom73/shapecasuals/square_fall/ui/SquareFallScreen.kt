package com.danylom73.shapecasuals.square_fall.ui

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
import com.danylom73.shapecasuals.square_fall.logic.runGameLoop
import com.danylom73.shapecasuals.square_fall.logic.spawnFallingObjects
import com.danylom73.shapecasuals.square_fall.model.GameState
import com.danylom73.shapecasuals.square_fall.model.Player
import com.danylom73.shapecasuals.square_fall.ui.components.drawFallingObjects
import com.danylom73.shapecasuals.square_fall.ui.components.drawPlayer
import com.danylom73.shapecasuals.square_fall.ui.components.drawTrackBackground
import com.danylom73.shapecasuals.square_fall.util.TRACK_PADDING
import com.danylom73.shapecasuals.ui.navigation.Screen
import com.danylom73.shapecasuals.ui.theme.AppFontFamily
import com.danylom73.shapecasuals.ui.theme.blueGray
import com.danylom73.shapecasuals.ui.theme.brown
import com.danylom73.shapecasuals.ui.theme.darkRed
import com.danylom73.shapecasuals.ui.theme.lightBrown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SquareFallScreen(
    primaryColor: Color = darkRed,
    enemyColor: Color = blueGray,
    pointColor: Color = darkRed,
    trackColor: Color = brown,
    backgroundColor: Color = lightBrown,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scoreDao = remember { db.scoreDao() }

    var isPlaying by remember { mutableStateOf(true) }
    var score by remember { mutableIntStateOf(0) }

    val player = remember { Player(x = 0f, direction = 1f) }
    val gameState = remember { GameState() }
    val snapshot = remember { mutableStateOf(gameState.copy()) }

    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            updateBestScore(scoreDao, Screen.SquareFallGame.route, score)
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            launch(Dispatchers.Main) {
                spawnFallingObjects(
                    gameState.objects,
                    isPlaying,
                    Pair(
                        player.leftBound.toInt(),
                        player.rightBound.toInt()
                    )
                )
            }
            launch(Dispatchers.Main) {
                runGameLoop(gameState, player) {
                    snapshot.value = gameState.copy(
                        objects = gameState.objects
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
            tint = primaryColor,
            modifier = Modifier
                .padding(start = 20.dp, top = 50.dp)
                .size(50.dp)
                .clickable { onGoHome() }
        )

        Text(
            text = score.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            textAlign = TextAlign.Center,
            color = primaryColor,
            fontSize = 50.sp,
            fontFamily = AppFontFamily,
            fontWeight = FontWeight.Bold
        )

        Canvas(Modifier.fillMaxSize()) {
            player.leftBound = 3 * TRACK_PADDING
            player.rightBound = size.width - 3 * TRACK_PADDING

            val centerY = size.height * 0.75f

            drawTrackBackground(size, centerY, trackColor)

            val snap = snapshot.value

            drawPlayer(
                x = snap.playerX,
                y = centerY,
                radius = 60f,
                color = primaryColor
            )

            val result = drawFallingObjects(
                snap.objects,
                snap.playerX,
                centerY,
                onPoint = { score++ },
                onEnemy = { isPlaying = false },
                enemyColor = enemyColor,
                pointColor = pointColor
            )

            if (result.removed.isNotEmpty()) {
                gameState.objects.removeAll(result.removed)
            }
        }

        if (!isPlaying) {
            Button(
                onClick = {
                    gameState.objects.clear()
                    score = 0
                    isPlaying = true
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .size(80.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = primaryColor
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
