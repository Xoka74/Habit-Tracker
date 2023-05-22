package com.example.data.di

import com.example.data.data_sources.HabitApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService(
        httpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String
    ): HabitApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl() = "https://droid-test-server.doubletapp.ru/api/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient()
}