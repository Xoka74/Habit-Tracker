package view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.HabitDao
import filters.HabitFilter
import models.Habit

class HabitListViewModel(
    private val habitDao: HabitDao,
    application: Application
) : AndroidViewModel(application) {
    var habits: LiveData<List<Habit>> = habitDao.getAllHabits()

    private val mutableFilter = MutableLiveData<HabitFilter>().apply {
        value = HabitFilter()
    }

    val filter: LiveData<HabitFilter>
        get() = mutableFilter

    fun applyName(query : String){
        mutableFilter.value = filter.value?.apply { name = query }
    }

    fun orderByPriority() {
        habits = habitDao.getHabitsOrderedByPriority()
    }

    fun searchByName(filter: HabitFilter) {
        habits = habitDao.searchByName(filter.name ?: return)
    }
}