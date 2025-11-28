package com.example.vinyls.repositories

import android.app.Application
import android.util.Log
import com.example.vinyls.model.Artist
import com.example.vinyls.network.CacheManager
import com.example.vinyls.network.NetworkServiceAdapter

class ArtistsRepository(val application: Application) {

    suspend fun refreshArtistsData(): List<Artist>  {

        var potentialResp = CacheManager.getInstance(application.applicationContext).getArtists()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get artists from network")
            val artists = NetworkServiceAdapter.getInstance(application).getArtists()
            CacheManager.getInstance(application.applicationContext).addArtists(artists)
            return artists
        }else{
            Log.d("Cache decision", "return ${potentialResp.size} artists from cache")
            return potentialResp
        }
    }

    suspend fun getArtistDetail(artistId: Int): Artist {
        return NetworkServiceAdapter.getInstance(application).getArtistById(artistId)
    }
}
