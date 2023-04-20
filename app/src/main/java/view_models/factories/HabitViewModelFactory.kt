package view_models.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import data.HabitDao
import view_models.HabitViewModel

class HabitViewModelFactory(private val habitDao: HabitDao) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        return HabitViewModel(habitDao, application as Application) as T
    }
}