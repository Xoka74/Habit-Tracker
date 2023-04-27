package com.example.hometask3.ui.habits_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hometask3.data.HabitDao
import com.example.hometask3.data.filters.HabitFilter
import com.example.hometask3.data.models.Habit

class HabitListViewModel(
    habitDao: HabitDao,
    application: Application
) : AndroidViewModel(application) {

    private val mutableFilter = MutableLiveData(HabitFilter())
    private val mutableHabits = MutableLiveData(listOf<Habit>())

    val roomHabits = habitDao.getAllHabits()


    fun setHabits(){
        mutableHabits.value = roomHabits.value
    }

    val habits: LiveData<List<Habit>>
        get() = mutableHabits

    val filter: LiveData<HabitFilter>
        get() = mutableFilter

    fun applyFilters(filter: HabitFilter) {
        mutableHabits.value = roomHabits.value?.filter { filter.apply(it) }
    }

    fun setFilter(filter: HabitFilter) {
        mutableFilter.value = filter
    }

    fun orderByPriority(descending: Boolean) {
        if (descending) {
            mutableHabits.value = mutableHabits.value?.sortedByDescending { it.priority.value }
        } else {
            mutableHabits.value = mutableHabits.value?.sortedBy { it.priority.value }
        }
    }
}