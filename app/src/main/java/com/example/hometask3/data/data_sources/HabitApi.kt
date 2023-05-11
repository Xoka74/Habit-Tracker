package com.example.hometask3.data.data_sources

import com.example.hometask3.data.data_sources.generics.ApiResult
import com.example.hometask3.data.models.converters.HabitConverter
import com.example.hometask3.data.models.entities.Habit
import com.example.hometask3.data.services.HabitApiService
import com.example.hometask3.utils.Network
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HabitApi {
    private val token = "54cd48d6-3c00-4d4e-9d81-0a1450b0d313"
    private val baseUrl = "https://droid-test-server.doubletapp.ru/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }.build()

    private val service = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create<HabitApiService>()

    private val habitConverter = HabitConverter()

    suspend fun getHabits(): ApiResult<List<Habit>> {
        return when (val result = Network.safeApiCall { service.getHabits(token) }) {
            is ApiResult.Success ->
                ApiResult.Success(result.data.map { habitConverter.dtoToHabit(it) })

            is ApiResult.Error -> ApiResult.Error(result.code, result.message)
            is ApiResult.Exception -> ApiResult.Exception(result.e)
        }
    }

    suspend fun addOrUpdate(habit: Habit): ApiResult<String> {
        val result = Network.safeApiCall {
            service.addOrUpdate(token, habitConverter.habitToDto(habit))
        }
        return when (result) {
            is ApiResult.Success ->
                ApiResult.Success(result.data.uid)
            is ApiResult.Error -> ApiResult.Error(result.code, result.message)
            is ApiResult.Exception -> ApiResult.Exception(result.e)
        }
    }
}