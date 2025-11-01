package com.example.vinyls

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

// This can also be in a separate file, e.g., ui/components/BottomNavigationBar.kt

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        // List of navigation items
        val navItems = listOf(
            // Define your items with routes, icons, and labels
            BottomNavItem("catalog", Icons.Filled.MusicNote, "Catalog"),
            BottomNavItem("artists", Icons.Filled.People, "Artists"),
            BottomNavItem("collectors", Icons.Filled.Group, "Collectors"),
            BottomNavItem("profile", Icons.Filled.AccountCircle, "Profile"),
        )

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Navigation logic to avoid stacking screens
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

// A simple data class for navigation items
data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)