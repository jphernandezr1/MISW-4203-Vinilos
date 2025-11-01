package com.example.vinyls.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Album
import com.example.vinyls.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsViewModel : ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchAlbums()
    }

    fun fetchAlbums() {
        _isLoading.value = true
        val call: Call<List<Album>> = RetrofitInstance.api.getAlbums()
        call.enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                viewModelScope.launch {
                    if (response.isSuccessful) {
                        _albums.value = response.body() ?: emptyList()
                    } else {
                        _albums.value = emptyList()
                    }
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                viewModelScope.launch {
                    _albums.value = emptyList()
                    _isLoading.value = false
                }
            }
        })
    }
}
