package com.danylom73.shapecasuals.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danylom73.shapecasuals.donut_spin.ui.DonutSpinScreen
import com.danylom73.shapecasuals.square_fall.ui.SquareFallScreen
import com.danylom73.shapecasuals.ui.home.HomeScreen
import com.danylom73.shapecasuals.vertical_run.ui.VerticalRunScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onOpenDonut = {
                    navController.navigate(Screen.DonutSpinGame.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onOpenSquareFall = {
                    navController.navigate(Screen.SquareFallGame.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onOpenVerticalRun = {
                    navController.navigate(Screen.VerticalRunGame.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.DonutSpinGame.route) {
            DonutSpinScreen {
                navController.popBackStack()
            }
        }

        composable(Screen.SquareFallGame.route) {
            SquareFallScreen {
                navController.popBackStack()
            }
        }

        composable(Screen.VerticalRunGame.route) {
            VerticalRunScreen {
                navController.popBackStack()
            }
        }
    }
}


