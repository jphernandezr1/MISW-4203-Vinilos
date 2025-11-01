package com.example.vinyls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.vinyls.model.Album
import com.example.vinyls.viewmodel.AlbumsViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(viewModel: AlbumsViewModel = viewModel()) {
    val albumsState by viewModel.albums.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(text = "Catalog", style = MaterialTheme.typography.headlineSmall, color = Color.White)

            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("Search for records") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp)
            )

            // Filters row (simple placeholders)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChipPlaceholder(text = "Genre")
                FilterChipPlaceholder(text = "Artist")
                FilterChipPlaceholder(text = "Price")
            }

            Text(text = "Featured", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), color = Color.White)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(albumsState) { album ->
                    AlbumCard(album)
                }
            }
        }
    }
}

@Composable
private fun FilterChipPlaceholder(text: String) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, color = Color.White)
        }
    }
}

@Composable
private fun AlbumCard(album: Album) {
    Card(shape = RoundedCornerShape(12.dp), modifier = Modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = album.cover,
                contentDescription = album.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = album.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(text = album.performers.joinToString { it.name }, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

// Simple preview with sample data
@Preview(showBackground = true)
@Composable
fun AlbumsPreview() {
    val sample = listOf(
        Album(100, "The Sound of Silence", "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg", null, null, null, null),
        Album(101, "Abbey Road", "https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg", null, null, null, null)
    )
    // Simple wrapper to preview grid
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1B0F22)).padding(16.dp)) {
        Text(text = "Catalog", style = MaterialTheme.typography.headlineSmall, color = Color.White)
    }
}
