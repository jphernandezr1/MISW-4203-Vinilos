package com.example.vinyls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinyls.ui.AlbumsScreen

// This can be in a new file or directly in your MainActivity if you prefer

@Composable
fun AppMainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigationBar(navController) }
    ) { innerPadding ->
        // Main content area
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "catalog") {
        composable("catalog") { AlbumsScreen() }
        composable("artists") { /* Placeholder for Artists Screen */ }
        composable("collectors") { /* Placeholder for Collectors Screen */ }
        composable("profile") { /* Placeholder for Profile Screen */ }
    }
}