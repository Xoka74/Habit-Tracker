package view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.HabitsRepository
import filters.HabitFilter
import models.Habit

class HabitsListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val mutableHabits = MutableLiveData<List<Habit>>()

    val habits: LiveData<List<Habit>>
        get() = mutableHabits

    init {
        mutableHabits.value = HabitsRepository.habits
    }

    fun applyFilters(filter: HabitFilter) {
        mutableHabits.postValue(HabitsRepository.habits.filter { filter.apply(it) })
    }

    fun orderByPriority(descending: Boolean) {
        if (descending) {
            mutableHabits.postValue(mutableHabits.value?.sortedByDescending { it.priority.value })
        } else {
            mutableHabits.postValue(mutableHabits.value?.sortedBy { it.priority.value })
        }
    }
}