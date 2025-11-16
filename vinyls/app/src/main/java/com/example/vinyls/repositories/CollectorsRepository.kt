package com.example.vinyls.repositories

import android.app.Application
import android.util.Log
import com.example.vinyls.model.Collector
import com.example.vinyls.network.CacheManager
import com.example.vinyls.network.NetworkServiceAdapter

class CollectorsRepository(val application: Application) {

    suspend fun refreshData(): List<Collector>  {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision collectors", "get from network")
            var collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(collectors)
            return collectors
        }else{
            Log.d("Cache decision", "return ${potentialResp.size} collectors from cache")
            return potentialResp
        }
    }

    suspend fun refreshCollectorData(collectorId: Int): Collector {
        return NetworkServiceAdapter.getInstance(application).getCollectorById(collectorId)
    }
}