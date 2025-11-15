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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vinyls.ui.AlbumsScreen
import com.example.vinyls.ui.CollectorDetailFragment
import com.example.vinyls.ui.CollectorsListFragment
import com.example.vinyls.ui.CollectorsListScreen
import com.example.vinyls.ui.Screen


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
    NavHost(navController = navController, startDestination = "catalog") {
        // Home Screen
        composable("catalog") {
            AppMainScreen(navController = navController) {  AlbumsScreen() }
        }
        composable("artists") { /* anadir aca */ }
        composable("collectors") {
            AppMainScreen(navController = navController) {CollectorsListScreen(navController = navController) }
        }
        composable("profile") { /* anadir aca */ }


        // Collector Detail Screen with parameter
        composable(
            route = Screen.CollectorDetail.route,
            arguments = listOf(
                navArgument("collectorId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val collectorId = backStackEntry.arguments?.getString("collectorId")
            CollectorDetailFragment(
                navController = navController,
//                collectorId = collectorId
            )
        }
    }

}