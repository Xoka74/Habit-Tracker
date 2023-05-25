package com.example.hometask3.presentation.ui.habit_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data_sources.HabitApiImpl
import com.example.domain.models.entities.Duration
import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType
import com.example.domain.models.entities.Priority
import com.example.domain.models.generics.ApiResult
import com.example.domain.repositories.HabitRepository
import com.example.domain.utils.DateUtils.nowDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApiImpl,
    app: Application
) : AndroidViewModel(app) {

    private val mutableHabit = MutableStateFlow(Habit())
    val habit: StateFlow<Habit>
        get() = mutableHabit.asStateFlow()

    fun triggerHabit(id: String?) {
        var habit = Habit()
        if (id != null) {
            habit = habitRepository.getHabitById(id) ?: Habit()
        }
        mutableHabit.value = habit
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            addOrUpdate()
        }
    }

    private suspend fun addOrUpdate() {
        var habit = habit.value.copy(creationDate = nowDate().time)
        val result = habitApi.addOrUpdate(habit)
        if (result is ApiResult.Success) {
            habit = habit.copy(uid = result.data, isSynced = true)
        }

        habitRepository.update(habit)
    }

    //region Triggers
    fun triggerName(name: String) {
        mutableHabit.value = mutableHabit.value.copy(name = name)
    }

    fun triggerDescription(desc: String) {
        mutableHabit.value = mutableHabit.value.copy(description = desc)
    }

    fun triggerTimesAmount(timesAmount: Int) {
        val periodicity = mutableHabit.value.periodicity.copy(intervalAmount = timesAmount)
        mutableHabit.value = mutableHabit.value.copy(periodicity = periodicity)
    }

    fun triggerInterval(duration: Duration) {
        val periodicity = mutableHabit.value.periodicity.copy(interval = duration)
        mutableHabit.value = mutableHabit.value.copy(periodicity = periodicity)
    }

    fun triggerCount(count: Int) {
        mutableHabit.value = mutableHabit.value.copy(count = count)
    }

    fun triggerPriority(priority: Priority) {
        mutableHabit.value = mutableHabit.value.copy(priority = priority)
    }

    fun triggerColor(color: Int) {
        mutableHabit.value = mutableHabit.value.copy(color = color)
    }

    fun triggerType(type: HabitType) {
        mutableHabit.value = mutableHabit.value.copy(type = type)
    }
    //endregion
}