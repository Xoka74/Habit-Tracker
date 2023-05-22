package com.example.domain.repositories

import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult

interface HabitApi {
    suspend fun getHabits(): ApiResult<List<Habit>>
    suspend fun addOrUpdate(habit: Habit): ApiResult<String>
}