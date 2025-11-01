package com.example.vinyls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vinyls.ui.CollectorsListScreen
import com.example.vinyls.ui.theme.VinylsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinylsTheme {
                Scaffold(
                    bottomBar = {
                        VinylsNavBar(
                            selectedTab = 2,
                            onTabSelected = {  }
                        )
                    },
                    modifier = Modifier.fillMaxSize())
                    { innerPadding ->
                        CollectorsListScreen()
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VinylsTheme {
        Text("Android")
    }
}


@Composable
fun VinylsNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF16162A),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Catalog",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    "Catalog",
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color(0xFF6B6B7E),
                unselectedTextColor = Color(0xFF6B6B7E),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_view),
                    contentDescription = "Artists",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    "Artists",
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color(0xFF6B6B7E),
                unselectedTextColor = Color(0xFF6B6B7E),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            if (selectedTab == 2) Color(0xFF7B3FD9) else Color.Transparent,
                            shape = RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_view),
                        contentDescription = "Collectors",
                        modifier = Modifier.size(28.dp),
                        tint = if (selectedTab == 2) Color.White else Color(0xFF6B6B7E)
                    )
                }
            },
            label = {
                Text(
                    "Collectors",
                    fontSize = 12.sp,
                    color = if (selectedTab == 2) Color(0xFF9B4DFF) else Color(0xFF6B6B7E)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF9B4DFF),
                unselectedIconColor = Color(0xFF6B6B7E),
                unselectedTextColor = Color(0xFF6B6B7E),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) },
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    "Profile",
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color(0xFF6B6B7E),
                unselectedTextColor = Color(0xFF6B6B7E),
                indicatorColor = Color.Transparent
            )
        )
    }
}