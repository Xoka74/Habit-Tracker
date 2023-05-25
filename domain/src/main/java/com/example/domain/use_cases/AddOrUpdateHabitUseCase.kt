package com.example.domain.use_cases

import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult
import com.example.domain.repositories.HabitApi
import com.example.domain.repositories.HabitRepository
import com.example.domain.utils.DateUtils
import com.example.domain.utils.DateUtils.nowDate

class AddOrUpdateHabitUseCase(
    private val habitApi : HabitApi,
    private val habitRepository: HabitRepository,
    private val habit : Habit
) {
    suspend operator fun invoke(){
        with(habit) {
            isSynced = true
            creationDate = nowDate().time
            val result = habitApi.addOrUpdate(this)
            if (result is ApiResult.Success) {
                uid = result.data
            }
            habitRepository.insert(this)
        }
    }
}