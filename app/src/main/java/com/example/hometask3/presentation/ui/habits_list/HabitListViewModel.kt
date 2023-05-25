package com.example.hometask3.presentation.ui.habits_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data_sources.HabitApiImpl
import com.example.domain.models.entities.Habit
import com.example.domain.models.generics.ApiResult
import com.example.domain.models.filters.HabitFilter
import com.example.domain.repositories.HabitRepository
import com.example.domain.utils.DateUtils.nowDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class HabitListViewModel(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApiImpl,
    app: Application
) : AndroidViewModel(app) {

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

    fun triggerFilter(filter: HabitFilter) {
        filterFlow.value = filter
    }

    fun performHabit(habit : Habit) : Habit {
        val doneDate = nowDate().time
        val newDoneDates = habit.doneDates.toMutableList()
            .apply { add(doneDate) }
        habit.doneDates = newDoneDates
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.update(habit)
            habitApi.habitDone(habit.uid, doneDate)
        }

        return habit
    }
}


