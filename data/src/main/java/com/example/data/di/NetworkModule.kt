package com.example.data.di

import com.example.data.data_sources.HabitApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }


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
    @Named("token")
    fun provideToken() = "54cd48d6-3c00-4d4e-9d81-0a1450b0d313"
}