package view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.HabitsRepository
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

    fun addHabit(habit: Habit) {
        HabitsRepository.addHabit(habit)
    }

    fun deleteHabit(habit: Habit) {
        HabitsRepository.deleteHabit(habit)
    }

    fun changeHabit(oldHabit: Habit, newHabit: Habit) {
        HabitsRepository.changeHabit(oldHabit, newHabit)
    }
}