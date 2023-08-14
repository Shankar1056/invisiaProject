package com.example.invisiaproject.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://run.mocky.io/v3/"

@Module
@InstallIn(SingletonComponent::class)
class RetrofitInstanceModule {

    @Provides
    @Singleton
    fun theRetrofitInstance() : APIService {
        val API : APIService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
        return API
    }

}