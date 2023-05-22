package com.example.domain.use_cases

import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult
import com.example.domain.repositories.HabitApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadHabitsUseCase(
    private val habitApi: HabitApi,
) {

    operator fun invoke(): Flow<List<Habit>> {
        return flow {
            while (true) {
                val response = habitApi.getHabits()
                if (response is ApiResult.Success<List<Habit>>) {
                    emit(response.data)
                    break
                }
                delay(2500)
            }
        }
    }
}