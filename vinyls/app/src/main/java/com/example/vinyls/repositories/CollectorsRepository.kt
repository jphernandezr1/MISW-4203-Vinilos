package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.models.Collector

class CollectorsRepository(val application: Application) {

    fun refreshData(): List<Collector>  {
        return listOf()
    }
}