package com.example.data.data_sources

import com.example.data.utils.Network
import com.example.domain.models.converters.HabitConverter
import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult
import javax.inject.Inject

class HabitApiImpl @Inject constructor(
    private val service: HabitApiService
) {
    private val token = "54cd48d6-3c00-4d4e-9d81-0a1450b0d313"
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