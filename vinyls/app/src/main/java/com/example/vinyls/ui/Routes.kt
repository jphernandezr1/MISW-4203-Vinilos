package com.example.vinyls.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vinyls.AppMainScreen



sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CollectorDetail : Screen("collector_detail/{collectorId}") {
        fun createRoute(collectorId: Int) = "collector_detail/$collectorId"
    }
    // Add more screens as needed
}
