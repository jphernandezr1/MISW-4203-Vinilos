package com.example.vinyls.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Retrofit requires the base URL to end with '/'
    private const val BASE_URL = "https://back-vinyls.victoriousground-8087781c.westus2.azurecontainerapps.io/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
