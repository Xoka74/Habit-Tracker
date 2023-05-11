package com.example.hometask3.ui.habit_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hometask3.data.data_sources.HabitApi
import com.example.hometask3.data.data_sources.generics.ApiResult
import com.example.hometask3.data.models.entities.Duration
import com.example.hometask3.data.models.entities.Habit
import com.example.hometask3.data.models.entities.HabitType
import com.example.hometask3.data.models.entities.Priority
import com.example.hometask3.data.repositories.HabitRepository
import com.example.hometask3.utils.DateUtils.nowDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApi,
    app: Application
) : AndroidViewModel(app) {

    private val mutableHabit = MutableLiveData(Habit())
    val habit: LiveData<Habit>
        get() = mutableHabit

    fun triggerHabit(habit: Habit?) {
        mutableHabit.value = habit
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            addOrUpdate()
        }
    }

    private suspend fun addOrUpdate(){
        habit.value?.let {
            it.isSynced = true
            it.date = nowDate()
            val result = habitApi.addOrUpdate(it)
            if (result is ApiResult.Success){
                it.uid = result.data
            }
            habitRepository.insert(it)
        }
    }

    //region Triggers
    fun triggerName(name: String) {
        triggerHabit(mutableHabit.value.apply { this?.name = name })
    }

    fun triggerDescription(desc: String) {
        triggerHabit(mutableHabit.value.apply { this?.description = desc })
    }

    fun triggerTimesAmount(timesAmount: Int) {
        triggerHabit(mutableHabit.value.apply { this?.periodicity?.intervalAmount = timesAmount })
    }

    fun triggerCompletionAmount(completionAmount: Int) {
        triggerHabit(mutableHabit.value.apply { this?.count = completionAmount })
    }

    fun triggerPriority(priority: Priority) {
        triggerHabit(mutableHabit.value.apply { this?.priority = priority })
    }

    fun triggerInterval(duration: Duration) {
        triggerHabit(mutableHabit.value.apply { this?.periodicity?.interval = duration })
    }

    fun triggerColor(color: Int) {
        triggerHabit(mutableHabit.value.apply { this?.color = color })
    }

    fun triggerType(type: HabitType) {
        triggerHabit(mutableHabit.value.apply { this?.type = type })
    }
    //endregion
}