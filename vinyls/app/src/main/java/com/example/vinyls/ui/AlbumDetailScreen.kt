package com.example.vinyls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinyls.model.Track
import com.example.vinyls.viewmodel.AlbumDetailUiState
import com.example.vinyls.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(
    navController: NavController,
    albumId: Int,
    viewModel: AlbumDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(albumId) {
        viewModel.loadAlbum(albumId)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when {
            uiState.isLoading -> DetailLoadingState()
            uiState.errorMessage != null -> DetailErrorState(uiState.errorMessage) {
                viewModel.loadAlbum(albumId)
            }
            uiState.album != null -> AlbumDetailContent(
                uiState,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun DetailLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun DetailErrorState(message: String?, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message ?: "Something went wrong", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun AlbumDetailContent(
    uiState: AlbumDetailUiState,
    onBack: () -> Unit
) {
    val album = uiState.album ?: return
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .testTag("album_detail_screen")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = album.cover,
                contentDescription = album.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC0B0614))
                        )
                    )
            )
            IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(text = album.name, style = MaterialTheme.typography.headlineMedium, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildMetaLine(
                        performers = album.performers.joinToString { it.name },
                        releaseDate = album.releaseDate,
                        genre = album.genre
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFBB86FC)
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Text(
                text = album.description.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .testTag("album_detail_description")
            )

            Text(
                text = "Tracklist",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.testTag("album_detail_tracklist_title")
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrackList(tracks = album.tracks)

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("album_detail_add_collection_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB347FF))
            ) {
                Text("Add to Collection", fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("album_detail_add_wishlist_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1340))
            ) {
                Text("Add to Wishlist", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

private fun buildMetaLine(performers: String, releaseDate: String?, genre: String?): String {
    val parts = mutableListOf<String>()
    if (performers.isNotBlank()) parts.add(performers)
    releaseDate?.takeIf { it.length >= 4 }?.let { parts.add(it.substring(0, 4)) }
    if (!genre.isNullOrBlank()) parts.add(genre)
    return parts.joinToString(" · ")
}

@Composable
private fun TrackList(tracks: List<Track>) {
    if (tracks.isEmpty()) {
        Text(text = "Tracklist not available", color = Color(0xFFB7A1D8))
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tracks.forEachIndexed { index, track ->
                TrackRow(number = index + 1, track = track)
            }
        }
    }
}

@Composable
private fun TrackRow(number: Int, track: Track) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1D0F2E))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$number.",
                color = Color(0xFFB7A1D8),
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = track.name, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Text(text = track.duration ?: "--", color = Color(0xFFB7A1D8))
        }
    }
}
