package com.example.vinyls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.vinyls.ui.theme.VinylsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            VinylsTheme {
//                AppMainScreen()
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

