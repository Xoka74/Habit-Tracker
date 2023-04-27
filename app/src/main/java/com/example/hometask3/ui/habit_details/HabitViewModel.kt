package com.example.hometask3.ui.habit_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hometask3.data.HabitDao
import com.example.hometask3.data.models.Duration
import com.example.hometask3.data.models.Habit
import com.example.hometask3.data.models.HabitType
import com.example.hometask3.data.models.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitDao: HabitDao, application: Application
) : AndroidViewModel(application) {

    private val tag  = "HabitViewModel"
    private val mutableHabit = MutableLiveData<Habit?>()
    val habit: LiveData<Habit?>
        get() = mutableHabit

    fun postHabit(habit: Habit?) {
        mutableHabit.value = habit
    }

    fun edit() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(tag, "Edited in ${Thread.currentThread().name}")
            habit.value?.let { habitDao.update(it) }
        }
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(tag, "Saved in ${Thread.currentThread().name}")
            habit.value?.let { habitDao.insert(it) }
        }
    }


    fun triggerName(name: String) {
        postHabit(mutableHabit.value.apply { this?.name = name })
    }

    fun triggerDescription(desc: String) {
        postHabit(mutableHabit.value.apply { this?.description = desc })
    }

    fun triggerTimesAmount(timesAmount: Int) {
        postHabit(mutableHabit.value.apply { this?.periodicity?.intervalAmount = timesAmount })
    }

    fun triggerCompletionAmount(completionAmount: Int) {
        postHabit(mutableHabit.value.apply { this?.completionAmount = completionAmount })
    }

    fun triggerPriority(priority: Priority) {
        postHabit(mutableHabit.value.apply { this?.priority = priority })
    }

    fun triggerInterval(duration : Duration){
        postHabit(mutableHabit.value.apply { this?.periodicity?.interval = duration })
    }

    fun triggerColor(color : Int){
        postHabit(mutableHabit.value.apply { this?.color = color })
    }

    fun triggerType(type : HabitType){
        postHabit(mutableHabit.value.apply { this?.type = type })
    }
}