package com.example.invisiaproject.ui

import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("4d92f884-ac04-475e-829a-49f772b0ff20/")
    suspend fun getHotelRegion(
    ) : Response<HotelRegionModel>

}