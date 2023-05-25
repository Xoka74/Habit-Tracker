package com.example.data.data_sources

import com.example.data.utils.Network
import com.example.domain.models.converters.HabitConverter
import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult
import com.example.domain.models.requests.HabitDoneBody
import javax.inject.Inject

class HabitApiImpl @Inject constructor(
    private val service: HabitApiService,
    private val token : String,
) {

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

    suspend fun habitDone(uid: String, date: Long) : ApiResult<String> {
        return Network.safeApiCall {
            service.habitDone(token, HabitDoneBody(uid, (date / 1000).toInt()))
        }
    }
}