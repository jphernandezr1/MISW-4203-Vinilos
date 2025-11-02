package com.example.vinyls.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls.model.Collector
import com.example.vinyls.repositories.CollectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorsViewModel(application: Application) :  AndroidViewModel(application) {

    private val _collectors = MutableLiveData<List<Collector>>()
    private val collectorsRepository = CollectorsRepository(application)

    val collectors: LiveData<List<Collector>>
        get() = _collectors

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch(Dispatchers.Default){
            withContext(Dispatchers.IO){
                val data = collectorsRepository.refreshData()
                _collectors.postValue(data)
            }
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}