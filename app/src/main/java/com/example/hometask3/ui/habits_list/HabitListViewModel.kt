package com.example.hometask3.ui.habits_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hometask3.data.data_sources.HabitApi
import com.example.hometask3.data.data_sources.generics.ApiResult
import com.example.hometask3.data.filters.HabitFilter
import com.example.hometask3.data.repositories.HabitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class HabitListViewModel(
    habitRepository: HabitRepository,
    private val habitApi: HabitApi, app: Application
) : AndroidViewModel(app) {

//    private var roomHabits = habitRepository.getAllHabits()
    private val filterFlow = MutableStateFlow(HabitFilter())

    val habits = flow {
        while (true) {
            val response = habitApi.getHabits()
            if (response is ApiResult.Success) {
                emit(response.data)
                break
            }
            delay(2500)
        }
    }.combine(filterFlow) { habits, filter ->
        habits.filter { filter.apply(it) }
    }

    fun setFilter(filter: HabitFilter) {
        filterFlow.value = filter
    }
}
