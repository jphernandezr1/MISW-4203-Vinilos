package com.example.vinyls.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls.model.Album
import com.example.vinyls.model.Artist
import com.example.vinyls.model.Collector
import com.example.vinyls.model.CollectorAlbum
import com.example.vinyls.model.CollectorPerformer
import com.example.vinyls.model.Comment
import com.example.vinyls.model.Performer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL =
            "https://back-vinyls.victoriousground-8087781c.westus2.azurecontainerapps.io"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { cont ->
        requestQueue.add(
            getRequest(
                "/collectors",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    var item: JSONObject
                    var collector: Collector
                    (0 until resp.length()).forEach {
                        item = resp.getJSONObject(it)
                        collector = Collector(
                            id = item.getInt("id"),
                            name = item.optString("name"),
                            telephone = item.optString("telephone"),
                            email = item.optString("email"),
                            comments = mutableListOf<Comment>(),
                            favoritePerformers = mutableListOf(),
                            collectorAlbums = mutableListOf(),
                        )
                        list.add(it, collector)
                    }
                    cont.resume(list)
                },
                {

                    cont.resumeWithException(it)
                })
        )
    }

    suspend fun getCollectorById(collectorId: Int) = suspendCoroutine<Collector> { cont ->
        requestQueue.add(
            getRequest(
                "/collectors/$collectorId",
                { response ->
                    val resp = JSONObject(response)

                    val comments = mutableListOf<Comment>()
                    val commentsArray = resp.getJSONArray("comments")

                    (0 until commentsArray.length()).forEach {
                        val comment = commentsArray.getJSONObject(it)
                        comments.add(
                            Comment(
                                id = comment.getInt("id"),
                                rating = comment.getInt("rating"),
                                description = comment.optString("description"),
                            )
                        )
                    }

                    val favoritePerformers = mutableListOf<CollectorPerformer>()
                    val favoritePerformersArray = resp.getJSONArray("favoritePerformers")

                    (0 until favoritePerformersArray.length()).forEach {
                        val performer = favoritePerformersArray.getJSONObject(it)

                        favoritePerformers.add(
                            CollectorPerformer(
                                id = performer.getInt("id"),
                                name = performer.optString("name"),
                                image = performer.optString("image"),
                                description = performer.optString("description"),
                                birthDate = performer.optString("birthDate"),
                            )
                        )
                    }

                    val collectorsAlbums = mutableListOf<CollectorAlbum>()
                    val collectorsAlbumsArray = resp.getJSONArray("collectorAlbums")

                    (0 until collectorsAlbumsArray.length()).forEach {
                        val album = collectorsAlbumsArray.getJSONObject(it)

                        collectorsAlbums.add(
                            CollectorAlbum(
                                id = album.getInt("id"),
                                price = album.getInt("price"),
                                status = album.optString("status"),
                            )
                        )
                    }

                    val collector = Collector(
                        id = resp.getInt("id"),
                        name = resp.optString("name"),
                        telephone = resp.optString("telephone"),
                        email = resp.optString("email"),
                        comments = comments,
                        favoritePerformers = favoritePerformers,
                        collectorAlbums = collectorsAlbums
                    )

                    cont.resume(collector)

                },
                {

                    cont.resumeWithException(it)
                })
        )

    }


    suspend fun getArtists() = suspendCoroutine<List<Artist>> { cont ->
        requestQueue.add(
            getRequest(
                "/musicians",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    var item: JSONObject
                    var artist: Artist
                    (0 until resp.length()).forEach {
                        item = resp.getJSONObject(it)
                        artist = Artist(
                            id = item.getInt("id"),
                            name = item.optString("name"),
                            description = item.optString("description"),
                            image = item.optString("image"),
                        )
                        list.add(it, artist)
                    }
                    cont.resume(list)
                },
                {

                    cont.resumeWithException(it)
                })
        )
    }
}