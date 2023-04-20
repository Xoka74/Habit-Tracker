package view_models

import android.app.Application
import android.icu.util.TimeUnit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.HabitDao
import models.Duration
import models.Habit
import models.HabitType
import models.Priority

class HabitViewModel(
    private val habitDao: HabitDao, application: Application
) : AndroidViewModel(application) {
    private val mutableHabit = MutableLiveData<Habit?>()
    val habit: LiveData<Habit?>
        get() = mutableHabit

    fun postHabit(habit: Habit?) {
        mutableHabit.value = habit
    }

    fun edit() {
        habit.value?.let { habitDao.update(it) }
    }

    fun save() {
        habit.value?.let { habitDao.insert(it) }
    }

    fun deleteHabit() {
        habit.value?.let { habitDao.delete(it) }
    }

    fun changeName(name: String) {
        postHabit(mutableHabit.value.apply { this?.name = name })
    }

    fun changeDescription(desc: String) {
        postHabit(mutableHabit.value.apply { this?.description = desc })
    }

    fun changeTimesAmount(timesAmount: Int) {
        postHabit(mutableHabit.value.apply { this?.periodicity?.intervalAmount = timesAmount })
    }

    fun changeCompletionAmount(completionAmount: Int) {
        postHabit(mutableHabit.value.apply { this?.completionAmount = completionAmount })
    }

    fun changePriority(priority: Priority) {
        postHabit(mutableHabit.value.apply { this?.priority = priority })
    }

    fun changeInterval(duration : Duration){
        postHabit(mutableHabit.value.apply { this?.periodicity?.interval = duration })
    }

    fun changeColor(color : Int){
        postHabit(mutableHabit.value.apply { this?.color = color })
    }

    fun changeType(type : HabitType){
        postHabit(mutableHabit.value.apply { this?.type = type })
    }
}