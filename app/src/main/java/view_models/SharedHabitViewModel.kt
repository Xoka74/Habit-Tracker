package view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import models.Habit

class SharedHabitViewModel(
    application: Application
) : AndroidViewModel(application) {
    val mutableHabit = MutableLiveData<Habit>()
}