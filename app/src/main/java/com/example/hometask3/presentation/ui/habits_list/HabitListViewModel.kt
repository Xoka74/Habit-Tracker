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
    val roomHabits = habitRepository.getAllHabits()
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

    fun performHabit(habit : Habit) : List<Long> {
        val doneDate = nowDate().time
        val timePeriod = habit.periodicity.toLong()

        val timePeriodCount = (doneDate - habit.creationDate) / timePeriod
        val newDoneDates = habit.doneDates.toMutableList()
            .apply { add(doneDate) }

        val resultDoneDates = newDoneDates.filter { timePeriodCount == ((it - habit.creationDate) / timePeriod) }

        habit.doneDates = newDoneDates
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.update(habit)
            habitApi.habitDone(habit.uid, doneDate)
        }

        return resultDoneDates
    }
}


