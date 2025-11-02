package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.model.Collector
import com.example.vinyls.network.NetworkServiceAdapter

class CollectorsRepository(val application: Application) {

    suspend fun refreshData(): List<Collector>  {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
}