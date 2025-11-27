package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.model.Album
import com.example.vinyls.model.AlbumToCreate
import com.example.vinyls.network.NetworkServiceAdapter

class AlbumRepository (val application: Application) {

    suspend fun addAlbum(album: AlbumToCreate): Album {
        return NetworkServiceAdapter.getInstance(application).addAlbum(album)
    }
}