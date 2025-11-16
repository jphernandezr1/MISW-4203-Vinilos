package com.example.vinyls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vinyls.ui.AlbumDetailScreen
import com.example.vinyls.ui.AlbumsScreen
import com.example.vinyls.ui.ArtistListScreen
import com.example.vinyls.ui.CollectorDetailFragment
import com.example.vinyls.ui.CollectorsListScreen


@Composable
fun AppMainScreen(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = { AppBottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}


@Composable
fun AppNavHost(navController: NavHostController) {
    AppMainScreen(navController = navController) {
        NavHost(navController = navController, startDestination = "catalog") {
            composable("catalog") {
                AlbumsScreen(navController = navController)
            }
            composable(
                route = "album_detail/{albumId}",
                arguments = listOf(
                    navArgument("albumId") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val albumId = backStackEntry.arguments?.getInt("albumId") ?: return@composable
                AlbumDetailScreen(navController = navController, albumId = albumId)
            }
            composable("artists") {
                ArtistListScreen(navController = navController)
            }
            composable("collectors") {
                CollectorsListScreen(navController = navController)
            }
            composable("profile") { /* anadir aca */ }

            composable(
                route = "collector_detail/{collectorId}",
                arguments = listOf(
                    navArgument("collectorId") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val collectorId = backStackEntry.arguments?.getInt("collectorId")
                CollectorDetailFragment(
                    navController = navController,
                    collectorId = collectorId
                )
            }
        }
    }
}