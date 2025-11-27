package com.example.tsdc_vinilos_equipo6.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Album
import com.example.vinyls.model.AlbumToCreate
import com.example.vinyls.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAlbumViewModel(application: Application) : AndroidViewModel(application){

    private val _album = MutableLiveData<Album>()
    private val _albumRepository = AlbumRepository(application)

    var album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        album = MutableLiveData()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun addAlbum(newAlbum: AlbumToCreate): Boolean {
        return try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    _albumRepository.addAlbum(newAlbum)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
            true
        } catch (e:Exception){
            _eventNetworkError.value = true
            false
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}