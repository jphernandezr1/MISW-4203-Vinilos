package com.example.vinyls.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Artist
import com.example.vinyls.repositories.ArtistsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ArtistDetailUiState(
    val isLoading: Boolean = true,
    val artist: Artist? = null,
    val relatedArtists: List<Artist> = emptyList(),
    val errorMessage: String? = null
)

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArtistsRepository(application)

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> = _uiState

    fun loadArtist(artistId: Int) {
        val currentArtist = _uiState.value.artist
        if (currentArtist?.id == artistId && !_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = ArtistDetailUiState(isLoading = true)
            try {
                val artistDeferred = async { repository.getArtistDetail(artistId) }
                val relatedDeferred = async { repository.refreshArtistsData() }

                val artist = artistDeferred.await()
                val related = relatedDeferred.await().filter { it.id != artistId }.take(5)

                _uiState.value = ArtistDetailUiState(
                    isLoading = false,
                    artist = artist,
                    relatedArtists = related
                )
            } catch (ex: Exception) {
                _uiState.value = ArtistDetailUiState(
                    isLoading = false,
                    artist = null,
                    relatedArtists = emptyList(),
                    errorMessage = ex.localizedMessage ?: "Unable to load artist"
                )
            }
        }
    }
}
