package com.danylom73.shapecasuals.donut_spin.ui

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
import androidx.compose.ui.geometry.Offset
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
import com.danylom73.shapecasuals.donut_spin.logic.runGameLoop
import com.danylom73.shapecasuals.donut_spin.logic.spawnEnemies
import com.danylom73.shapecasuals.donut_spin.model.Player
import com.danylom73.shapecasuals.donut_spin.model.Point
import com.danylom73.shapecasuals.donut_spin.model.State
import com.danylom73.shapecasuals.donut_spin.ui.components.drawDonut
import com.danylom73.shapecasuals.donut_spin.ui.components.drawPlayer
import com.danylom73.shapecasuals.donut_spin.ui.components.drawEnemies
import com.danylom73.shapecasuals.donut_spin.ui.components.drawPoint
import com.danylom73.shapecasuals.ui.navigation.Screen
import com.danylom73.shapecasuals.ui.theme.AppFontFamily
import com.danylom73.shapecasuals.ui.theme.darkBlue
import com.danylom73.shapecasuals.ui.theme.darkerLightGreen
import com.danylom73.shapecasuals.ui.theme.lightGreen
import com.danylom73.shapecasuals.ui.theme.white
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DonutSpinScreen(
    primaryColor: Color = white,
    secondaryColor: Color = darkerLightGreen,
    tertiaryColor: Color = lightGreen,
    backgroundColor: Color = darkBlue,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scoreDao = remember { db.scoreDao() }

    val player = remember { Player(angle = 0f) }
    val gameState = remember { State() }

    var isPlaying by remember { mutableStateOf(true) }
    var score by remember { mutableIntStateOf(0) }

    val stateSnapshot = remember { mutableStateOf(gameState.copy()) }

    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            updateBestScore(scoreDao, Screen.DonutSpinGame.route, score)
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            launch(Dispatchers.Main) {
                spawnEnemies(
                    gameState.enemies,
                    isPlaying
                )
            }
            launch(Dispatchers.Main) {
                runGameLoop(gameState, isPlaying) {
                    stateSnapshot.value = gameState.copy(
                        enemies = gameState.enemies
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
                    gameState.direction *= -1f
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
            val center = Offset(size.width / 2, size.height / 2)
            val snapshot = stateSnapshot.value

            drawDonut(
                center,
                tertiaryColor,
                backgroundColor
            )
            drawPlayer(
                center,
                player.copy(angle = snapshot.angle),
                primaryColor
            )
            val pointCollision = drawPoint(
                center,
                snapshot.points.first(),
                player.copy(angle = snapshot.angle),
                primaryColor,
                snapshot.pointAngle
            )
            val enemyCollision = drawEnemies(
                center,
                snapshot.enemies,
                player.copy(angle = snapshot.angle),
                secondaryColor
            )

            if (enemyCollision) isPlaying = false

            if (pointCollision) {
                score++
                gameState.points.clear()
                gameState.points.add(Point())
            }
        }

        if (!isPlaying) {
            Button(
                onClick = {
                    gameState.enemies.clear()
                    score = 0
                    isPlaying = true
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
                    .size(80.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = tertiaryColor
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
