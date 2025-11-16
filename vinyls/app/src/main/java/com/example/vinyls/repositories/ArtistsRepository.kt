package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.model.Artist
import com.example.vinyls.model.Collector
import com.example.vinyls.network.NetworkServiceAdapter

class ArtistsRepository(val application: Application) {

    suspend fun refreshArtistsData(): List<Artist>  {
        return NetworkServiceAdapter.getInstance(application).getArtists()
    }

}
