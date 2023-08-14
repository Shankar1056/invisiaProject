package com.example.invisiaproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val retrofitInstance: APIService): ViewModel() {


    val responseContainer = MutableLiveData<HotelRegionModel>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler() { _, throwable ->
        onError("Exception handled : ${throwable.localizedMessage}")
    }



    fun getMoviesFromAPI() {
        isShowProgress.value = true
        try {
            val job = viewModelScope.launch {
                val response = retrofitInstance.getHotelRegion()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        responseContainer.postValue(response.body())
                        isShowProgress.value = false
                    } else {
                        onError("Error : ${response.message()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.message
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        isShowProgress.value = false
    }

}