package com.danylom73.shapecasuals.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object DonutSpinGame: Screen("donut_spin")
    object SquareFallGame: Screen("square_fall")
    object VerticalRunGame: Screen("vertical_run")
}