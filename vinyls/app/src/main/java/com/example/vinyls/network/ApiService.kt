package com.example.vinyls.network

import com.example.vinyls.model.Album
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/albums/{albumId}")
    fun getAlbum(@Path("albumId") albumId: Int): Call<Album>
}
