package com.example.vinyls.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinyls.model.Collector
import com.example.vinyls.repositories.CollectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(application: Application, collectorId: Int) :  AndroidViewModel(application) {

    private val _collector = MutableLiveData<Collector>()
    private val collectorsRepository = CollectorsRepository(application)

    private val _collectorId = collectorId

    val collector: LiveData<Collector>
        get() = _collector

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch(Dispatchers.Default){
            withContext(Dispatchers.IO){
                val data = collectorsRepository.refreshCollectorData(_collectorId)
                _collector.postValue(data)
            }
        }

    }

    class Factory(val app: Application, val collectorId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app, collectorId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}