package com.example.vinyls.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class CollectorsListFragment{
}

data class Collector(
    val name: String,
    val username: String,
    val avatarColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorsListScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(2) }

    val collectors = listOf(
        Collector("Ethan Carter", "@music_lover_123", Color(0xFF6B8E7F)),
        Collector("Sophia Bennett", "@vinyl_enthusiast", Color(0xFFD4A574)),
        Collector("Liam Harper", "@record_collector", Color(0xFF7FA0B8)),
        Collector("Olivia Hayes", "@melody_maker", Color(0xFFE8A87C)),
        Collector("Noah Foster", "@sound_seeker", Color(0xFF8B9D9F)),
        Collector("Ava Morgan", "@rhythm_rider", Color(0xFFE5C4A8))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Collectors",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0A0A14),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF0A0A14)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = { Text("Search collectors", color = Color.Gray) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                },
//                colors = TextFieldDefaults.colors(
//                    containerColor = Color(0xFF1E2A3A),
//                    focusedBorderColor = Color.Transparent,
//                    unfocusedBorderColor = Color.Transparent,
//                    Color = Color.White
//                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip("Location", Color(0xFF2A3A4A))
                FilterChip("Genres", Color(0xFF2A3A4A))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Collectors List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(collectors) { collector ->
                    CollectorItem(collector)
                }
            }
        }
    }
}

@Composable
fun FilterChip(text: String, backgroundColor: Color) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun CollectorItem(collector: Collector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            collector.avatarColor,
                            collector.avatarColor.copy(alpha = 0.7f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Collector Info
        Column {
            Text(
                collector.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                collector.username,
                fontSize = 14.sp,
                color = Color(0xFF9B9BAA)
            )
        }
    }
}
