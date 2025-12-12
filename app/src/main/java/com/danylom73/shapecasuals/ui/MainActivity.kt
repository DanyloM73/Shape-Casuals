package com.danylom73.shapecasuals.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.danylom73.shapecasuals.ui.navigation.NavigationGraph
import com.danylom73.shapecasuals.ui.theme.ShapeCasualsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShapeCasualsTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)
            }
        }
    }
}