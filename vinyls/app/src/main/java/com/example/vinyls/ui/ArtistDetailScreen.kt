package com.example.vinyls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinyls.model.Artist
import com.example.vinyls.model.ArtistAlbum
import com.example.vinyls.viewmodel.ArtistDetailUiState
import com.example.vinyls.viewmodel.ArtistDetailViewModel

@Composable
fun ArtistDetailScreen(
    navController: NavController,
    artistId: Int,
    viewModel: ArtistDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(artistId) {
        viewModel.loadArtist(artistId)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val artist = uiState.artist
        when {
            uiState.isLoading -> DetailLoadingState()
            uiState.errorMessage != null -> DetailErrorState(message = uiState.errorMessage) {
                viewModel.loadArtist(artistId)
            }
            artist != null -> ArtistDetailContent(
                artist = artist,
                relatedArtists = uiState.relatedArtists,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun ArtistDetailContent(
    artist: Artist,
    relatedArtists: List<Artist>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0614))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = artist.image,
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = artist.name, style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            val albumCount = remember(artist.albums) { artist.albums.size }
            val monthlyListeners = remember(artist.monthlyListeners) {
                val listeners = artist.monthlyListeners ?: 100_000
                if (listeners >= 1000) {
                    val value = listeners / 1000
                    "${value}K"
                } else listeners.toString()
            }
            Text(
                text = "$albumCount Albums · $monthlyListeners Monthly Listeners",
                color = Color(0xFFB7A1D8),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: follow */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB347FF))
            ) {
                Text("Follow", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Biography", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = artist.description.orEmpty(),
            color = Color(0xFFDDCBEA),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))
        DiscographySection(albums = artist.albums)

        Spacer(modifier = Modifier.height(24.dp))
        RelatedArtistsSection(relatedArtists = relatedArtists)
    }
}

@Composable
private fun DiscographySection(albums: List<ArtistAlbum>) {
    Column {
        Text(text = "Discography", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        if (albums.isEmpty()) {
            Text(text = "No albums available", color = Color(0xFFB7A1D8))
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(albums, key = { it.id }) { album ->
                    DiscographyCard(album)
                }
            }
        }
    }
}

@Composable
private fun DiscographyCard(album: ArtistAlbum) {
    Column(
        modifier = Modifier
            .width(140.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = album.cover,
                contentDescription = album.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = album.name, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
        album.releaseDate?.let {
            Text(text = it.take(4), color = Color(0xFFB7A1D8), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun RelatedArtistsSection(relatedArtists: List<Artist>) {
    Column {
        Text(text = "Related Artists", style = MaterialTheme.typography.titleLarge, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        if (relatedArtists.isEmpty()) {
            Text(text = "No related artists available", color = Color(0xFFB7A1D8))
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(relatedArtists, key = { it.id }) { artist ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp)) {
                        AsyncImage(
                            model = artist.image,
                            contentDescription = artist.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = artist.name,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${artist.albums.size} Albums",
                            color = Color(0xFFB7A1D8),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
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
