package com.example.vinyls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


// Data classes
data class CollectionItem(
    val title: String,
    val backgroundColor: Color,
    val hasVinyl: Boolean = false
)

data class ActivityItem(
    val title: String,
    val subtitle: String,
    val backgroundColor: Color
)

class CollectorDetailScreen {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorDetailFragment(
    navController: NavHostController,

    ) {
    val backgroundColor = Color(0xFF1A1625)
    val cardColor = Color(0xFF2A2336)
    val purpleAccent = Color(0xFF7B3FF2)

    val musicalTastes = listOf("Indie Rock", "Electronic", "Hip Hop", "Jazz", "Classical")

    val collections = listOf(
        CollectionItem("The Indie Sound", Color(0xFFE8D5C4), false),
        CollectionItem("Electronic Beats", Color(0xFFF5F5F5), true),
        CollectionItem("Hip Hop Classics", Color(0xFF2D2D2D), false)
    )

    val activities = listOf(
        ActivityItem("The Indie Sound", "Added to collection", Color(0xFFE8D5C4)),
        ActivityItem("Electronic Beats", "Added to collection", Color(0xFF2D2D2D)),
        ActivityItem("Hip Hop Classics", "Added to collection", Color(0xFFF5F5F5))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Collector",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Empty space to center the title
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Profile Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8D5C4)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder for avatar image
                        Text(
                            "👤",
                            fontSize = 64.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Ethan Carter",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        "Collector",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        "Joined 2021",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Stats Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard("120", "Records", modifier = Modifier.weight(1f), cardColor)
                    StatCard("80", "Followers", modifier = Modifier.weight(1f), cardColor)
                    StatCard("20", "Following", modifier = Modifier.weight(1f), cardColor)
                }
            }

            // Musical Taste Section
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Musical Taste",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Genre tags
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(musicalTastes) { genre ->
                        GenreChip(genre, purpleAccent)
                    }
                }
            }

            // Collection Highlights
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Collection Highlights",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(collections) { collection ->
                        CollectionCard(collection)
                    }
                }
            }

            // Activity Section
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Activity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            items(activities) { activity ->
                ActivityItem(activity)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier, cardColor: Color) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                label,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun GenreChip(genre: String, backgroundColor: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor.copy(alpha = 0.3f),
        border = null
    ) {
        Text(
            genre,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = backgroundColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CollectionCard(collection: CollectionItem) {
    Column(
        modifier = Modifier.width(180.dp)
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(collection.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (collection.hasVinyl) {
                // Vinyl record representation
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center)
                                .clip(CircleShape)
                                .background(Color.Black)
                        )
                    }
                }
            } else {
                // Simple icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
        }

        Text(
            collection.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ActivityItem(activity: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(activity.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                activity.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                activity.subtitle,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}