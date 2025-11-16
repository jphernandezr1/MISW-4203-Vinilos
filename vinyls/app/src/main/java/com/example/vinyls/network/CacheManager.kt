package com.example.vinyls.network

import android.content.Context
import com.example.vinyls.model.Artist
import com.example.vinyls.model.Collector

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var collectors:  List<Collector> = mutableListOf()

    private var artists:  List<Artist> = mutableListOf()

    fun addCollectors(newCollectors: List<Collector>){
        if (collectors.isEmpty()){
            collectors = newCollectors
        }
    }

    fun addArtists(newArtist: List<Artist>){
        if (artists.isEmpty()){
            artists = newArtist
        }
    }

    fun getCollectors() : List<Collector>{
        return collectors
    }

    fun getArtists() : List<Artist>{
        return artists
    }
}