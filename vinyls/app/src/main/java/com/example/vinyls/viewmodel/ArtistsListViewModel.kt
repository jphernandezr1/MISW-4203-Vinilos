package com.example.vinyls.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Artist
import com.example.vinyls.repositories.ArtistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistsListViewModel(application: Application) :  AndroidViewModel(application) {

    private val _artists = MutableLiveData<List<Artist>>()
    private val artistRepository = ArtistsRepository(application)

    val artists: LiveData<List<Artist>>
        get() = _artists

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch(Dispatchers.Default){
            withContext(Dispatchers.IO){
                val data = artistRepository.refreshArtistsData()
                _artists.postValue(data)
            }
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}