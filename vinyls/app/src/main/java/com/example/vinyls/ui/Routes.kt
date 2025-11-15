package com.example.vinyls.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vinyls.AppMainScreen


sealed class ScreenRoutes(val route: String) {
    object Home : ScreenRoutes("home")
    object CollectorDetail : ScreenRoutes("collector_detail/{collectorId}") {
        fun createRoute(collectorId: String) = "collector_detail/$collectorId"
    }
    // Add more screens as needed
}


// ========================================
// 1. Add Navigation Dependency
// ========================================
// In your app/build.gradle.kts file:
/*
dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.6")
}
*/

// ========================================
// 2. Define Navigation Routes
// ========================================

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CollectorDetail : Screen("collector_detail/{collectorId}") {
        fun createRoute(collectorId: String) = "collector_detail/$collectorId"
    }
    // Add more screens as needed
}
