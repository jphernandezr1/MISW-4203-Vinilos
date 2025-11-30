package com.example.vinyls.network

import com.example.vinyls.model.Album
import com.example.vinyls.model.Track
import com.example.vinyls.model.TrackRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/albums/{albumId}")
    fun getAlbum(@Path("albumId") albumId: Int): Call<Album>

    @POST("/albums/{albumId}/tracks")
    fun addTrack(
        @Path("albumId") albumId: Int,
        @Body track: TrackRequest
    ): Call<Track>
}
