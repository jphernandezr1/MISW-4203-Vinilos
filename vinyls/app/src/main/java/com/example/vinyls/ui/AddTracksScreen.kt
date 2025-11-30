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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinyls.model.Album
import com.example.vinyls.viewmodel.AddTracksViewModel
import com.example.vinyls.viewmodel.TrackFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTracksScreen(
    navController: NavController,
    albumId: Int,
    viewModel: AddTracksViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(albumId) {
        viewModel.loadAlbum(albumId)
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearErrorMessage()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            val handle = navController.previousBackStackEntry?.savedStateHandle
            handle?.set("tracks_updated", true)
            handle?.set("tracks_success_message", message)
            viewModel.clearSuccessMessage()
            navController.popBackStack()
        }
    }

    val pageBackground = MaterialTheme.colorScheme.background
    Scaffold(
        containerColor = pageBackground,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Tracks", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pageBackground,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = pageBackground
        ) {
            when {
                uiState.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }

                uiState.album == null -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Unable to load album", color = Color.White)
                }

                else -> {
                    val album = uiState.album
                    if (album != null) {
                        AddTracksContent(
                            album = album,
                            forms = uiState.forms,
                            isSaving = uiState.isSaving,
                            canSubmit = uiState.canSubmit,
                            onAddForm = viewModel::addForm,
                            onRemoveForm = viewModel::removeForm,
                            onUpdateTitle = viewModel::updateTitle,
                            onUpdateDuration = viewModel::updateDuration,
                            onUpdateNumber = viewModel::updateNumber,
                            onSubmit = { viewModel.submitTracks(album.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddTracksContent(
    album: Album,
    forms: List<TrackFormState>,
    isSaving: Boolean,
    canSubmit: Boolean,
    onAddForm: () -> Unit,
    onRemoveForm: (Long) -> Unit,
    onUpdateTitle: (Long, String) -> Unit,
    onUpdateDuration: (Long, String) -> Unit,
    onUpdateNumber: (Long, String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        AlbumSummaryHeader(album)
        Spacer(modifier = Modifier.height(24.dp))

        forms.forEachIndexed { index, form ->
            TrackFormCard(
                index = index + 1,
                formState = form,
                allowRemove = forms.size > 1,
                onRemove = { onRemoveForm(form.id) },
                onTitleChange = { onUpdateTitle(form.id, it) },
                onDurationChange = { onUpdateDuration(form.id, it) },
                onNumberChange = { onUpdateNumber(form.id, it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = onAddForm,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("add_track_add_form_button"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB347FF))
        ) {
            Text(text = "Add Another Track")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onSubmit,
            enabled = canSubmit && !isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("add_tracks_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB347FF))
        ) {
            Text(if (isSaving) "Saving..." else "Add Tracks", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun AlbumSummaryHeader(album: Album) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = album.cover,
            contentDescription = album.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1D0F2E))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = album.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = albumMetaLine(album),
                color = Color(0xFFB7A1D8),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun TrackFormCard(
    index: Int,
    formState: TrackFormState,
    allowRemove: Boolean,
    onRemove: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onNumberChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B0F2C))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E1F3F)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = index.toString(), color = Color.White, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Track $index", color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                if (allowRemove) {
                    IconButton(onClick = onRemove) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Remove track", tint = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = formState.title,
                onValueChange = onTitleChange,
                label = { Text("Track Title") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_tracks_title_input_${index}")
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = formState.duration,
                onValueChange = onDurationChange,
                label = { Text("Duration (e.g., 3:45)") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_tracks_duration_input_${index}")
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = formState.trackNumber,
                onValueChange = onNumberChange,
                label = { Text("Track Number") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_tracks_number_input_${index}")
            )
        }
    }
}

private fun albumMetaLine(album: Album): String {
    val parts = mutableListOf<String>()
    val performers = album.performers.joinToString { it.name }
    if (performers.isNotBlank()) parts.add(performers)
    album.releaseDate?.takeIf { it.length >= 4 }?.let { parts.add(it.substring(0, 4)) }
    if (!album.genre.isNullOrBlank()) parts.add(album.genre)
    return parts.joinToString(" · ")
}
