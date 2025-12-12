package com.danylom73.shapecasuals.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danylom73.shapecasuals.data.AppDatabase
import com.danylom73.shapecasuals.ui.navigation.Screen
import com.danylom73.shapecasuals.ui.theme.AppFontFamily
import com.danylom73.shapecasuals.ui.theme.brown
import com.danylom73.shapecasuals.ui.theme.darkerBlue
import com.danylom73.shapecasuals.ui.theme.lightGreen
import com.danylom73.shapecasuals.ui.theme.orange

@Composable
fun HomeScreen(
    onOpenDonut: () -> Unit,
    onOpenSquareFall: () -> Unit,
    onOpenVerticalRun: () -> Unit
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).scoreDao() }

    var squareBest by remember { mutableStateOf<Int?>(null) }
    var donutBest by remember { mutableStateOf<Int?>(null) }
    var verticalBest by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        squareBest = dao.getScoreForGame(Screen.SquareFallGame.route)?.bestScore
        donutBest = dao.getScoreForGame(Screen.DonutSpinGame.route)?.bestScore
        verticalBest = dao.getScoreForGame(Screen.VerticalRunGame.route)?.bestScore
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkerBlue)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Shape Casuals",
                fontSize = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = AppFontFamily,
                fontWeight = FontWeight.Bold
            )

            MenuCard(
                title = "Donut Spin",
                accentColor = lightGreen,
                bestScore = donutBest,
                onClick = onOpenDonut
            )
            MenuCard(
                title = "Square Fall",
                accentColor = brown,
                bestScore = squareBest,
                onClick = onOpenSquareFall
            )
            MenuCard(
                title = "Vertical Run",
                accentColor = orange,
                bestScore = verticalBest,
                onClick = onOpenVerticalRun
            )
        }
    }
}
