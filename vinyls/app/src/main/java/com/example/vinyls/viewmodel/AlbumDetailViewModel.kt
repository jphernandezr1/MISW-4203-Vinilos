package com.example.vinyls.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vinyls.model.Album
import com.example.vinyls.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class AlbumDetailUiState(
    val isLoading: Boolean = true,
    val album: Album? = null,
    val errorMessage: String? = null
)

class AlbumDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState

    fun loadAlbum(albumId: Int, forceReload: Boolean = false) {
        // Avoid refetch on same id when configuration changes unless forced
        val currentAlbum = _uiState.value.album
        if (!forceReload && currentAlbum?.id == albumId && !_uiState.value.isLoading) return

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        RetrofitInstance.api.getAlbum(albumId).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                _uiState.value = if (response.isSuccessful) {
                    AlbumDetailUiState(
                        isLoading = false,
                        album = response.body(),
                        errorMessage = null
                    )
                } else {
                    AlbumDetailUiState(
                        isLoading = false,
                        album = null,
                        errorMessage = "Unable to load album"
                    )
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                _uiState.value = AlbumDetailUiState(
                    isLoading = false,
                    album = null,
                    errorMessage = t.localizedMessage ?: "Network error"
                )
            }
        })
    }
}
