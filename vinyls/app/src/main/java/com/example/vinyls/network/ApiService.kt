package com.example.vinyls.network

import com.example.vinyls.model.Album
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/albums")
    fun getAlbums(): Call<List<Album>>
}
