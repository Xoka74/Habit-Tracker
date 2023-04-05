package view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.HabitsRepository
import models.Habit

class HabitViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val mutableHabit = MutableLiveData<Habit?>()
    val habit: LiveData<Habit?>
        get() = mutableHabit

    fun postHabit(habit: Habit) {
        mutableHabit.postValue(habit)
    }

    fun saveHabit(newHabit: Habit) {
        if (habit.value == null) {
            HabitsRepository.addHabit(newHabit)
        } else {
            HabitsRepository.changeHabit(habit.value!!, newHabit)
            mutableHabit.value = null
        }
    }

    fun deleteHabit(){
        HabitsRepository.deleteHabit(habit.value ?: return)
    }
}