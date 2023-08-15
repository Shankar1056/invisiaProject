package com.example.invisiaproject.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.invisiaproject.ui.APIService
import com.example.invisiaproject.ui.HotelRegionModel
import com.example.invisiaproject.ui.utility.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val retrofitInstance: APIService
) : AndroidViewModel(app) {


    val responseContainer = MutableLiveData<HotelRegionModel>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>(true)
    var isRegionDataFound = MutableLiveData<Boolean>(false)
    var isHotelDataFound = MutableLiveData<Boolean>(false)


    fun getMoviesFromAPI() {
        if (Utility.isNetworkAvailable(app)) {
            fetchDataFromNetwork()
        } else {
            errorMessage.value = "No internet connection"
        }

    }

    private fun fetchDataFromNetwork() {
        isShowProgress.value = true
        try {
            viewModelScope.launch {
                val response = retrofitInstance.getHotelRegion()
                withContext(Dispatchers.Main) {
                    isShowProgress.value = false
                    if (response.isSuccessful) {
                        responseContainer.postValue(response.body())
                    } else {
                        onError("Error : ${response.message()}")
                        errorMessage.postValue(response.message())
                    }
                }
            }
        } catch (e: Exception) {
            isShowProgress.value = true
            errorMessage.value = e.message
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isShowProgress.value = false
    }

}