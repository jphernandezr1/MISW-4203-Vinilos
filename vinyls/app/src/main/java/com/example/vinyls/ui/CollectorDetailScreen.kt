package com.example.vinyls.ui

import android.app.Application
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.vinyls.model.CollectorAlbum
import com.example.vinyls.model.CollectorPerformer
import com.example.vinyls.viewmodel.CollectorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorDetailFragment(
    navController: NavHostController? = null,
    collectorId: Int?
) {
    // 1. Obtén el contexto de la Aplicación
    val application = LocalContext.current.applicationContext as Application

    // 2. Crea una instancia de tu Factory, pasando la app y el ID
    val factory = CollectorViewModel.Factory(application, collectorId!!)

    // 3. Pasa la factory al composable viewModel()
    val viewModel: CollectorViewModel = viewModel(factory = factory)

    val collectorState by viewModel.collector.observeAsState()

    val backgroundColor = Color(0xFF1A1625)
    val cardColor = Color(0xFF2A2336)
    val purpleAccent = Color(0xFF7B3FF2)

    val musicalTastes = listOf("Indie Rock", "Electronic", "Hip Hop", "Jazz", "Classical")


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Collector Detail Screen",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  navController?.popBackStack() }) {
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
                        collectorState?.name ?: "Unknown",
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
                        collectorState?.email ?: "Loading email",
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
                    StatCard("${collectorState?.comments?.size}", "Comments", modifier = Modifier.weight(1f), cardColor)
                    StatCard("${collectorState?.favoritePerformers?.size}", "Performers", modifier = Modifier.weight(1f), cardColor)
                    StatCard("${collectorState?.collectorAlbums?.size}", "Albums", modifier = Modifier.weight(1f), cardColor)
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
                    "Favorite Performers",
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
                    items(collectorState?.favoritePerformers ?: mutableListOf()) { performer ->
                        CollectionCard(performer)
                    }
                }
            }

            // Activity Section
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Collector Albums",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            items(collectorState?.collectorAlbums ?: mutableListOf()) { album ->
                ActivityItem(album)
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
fun CollectionCard(performer: CollectorPerformer) {
    Column(
        modifier = Modifier.width(180.dp)
    ) {
        Card (
            shape = RoundedCornerShape(12.dp),
        ) {
            AsyncImage(
                model = performer.image,
                contentDescription = performer.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
        }


        Text(
            performer.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ActivityItem(album: CollectorAlbum) {
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
                .background(Color(0xFFE8D5C4)),
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
                "Album: ${album.id}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                "\$ ${album.price}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}