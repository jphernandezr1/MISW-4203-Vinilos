package com.example.vinyls.repositories

import com.example.vinyls.model.Album
import com.example.vinyls.model.Track
import com.example.vinyls.model.TrackRequest
import com.example.vinyls.network.ApiService
import com.example.vinyls.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface TrackManagementRepository {
    fun fetchAlbum(albumId: Int, onResult: (Result<Album>) -> Unit)
    fun addTrack(albumId: Int, request: TrackRequest, onResult: (Result<Track>) -> Unit)
}

class RemoteTrackManagementRepository(
    private val apiService: ApiService = RetrofitInstance.api
) : TrackManagementRepository {

    override fun fetchAlbum(albumId: Int, onResult: (Result<Album>) -> Unit) {
        apiService.getAlbum(albumId).enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(Result.success(it)) }
                        ?: onResult(Result.failure(IllegalStateException("Album empty")))
                } else {
                    onResult(Result.failure(httpError("Unable to load album", response)))
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    override fun addTrack(albumId: Int, request: TrackRequest, onResult: (Result<Track>) -> Unit) {
        apiService.addTrack(albumId, request).enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(Result.success(it)) }
                        ?: onResult(Result.failure(IllegalStateException("Track empty")))
                } else {
                    onResult(Result.failure(httpError("Unable to add track", response)))
                }
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    private fun <T> httpError(defaultMessage: String, response: Response<T>): Throwable {
        val errorBody = response.errorBody()?.string()?.takeIf { it.isNotBlank() }
        val message = buildString {
            append(defaultMessage)
            append(" (code ")
            append(response.code())
            append(")")
            if (errorBody != null) {
                append(": ")
                append(errorBody)
            }
        }
        return IllegalStateException(message)
    }
}
