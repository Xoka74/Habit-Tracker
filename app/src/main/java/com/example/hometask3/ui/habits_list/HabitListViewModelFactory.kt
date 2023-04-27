package com.example.hometask3.ui.habits_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.hometask3.data.HabitDao

class HabitListViewModelFactory(private val habitDao: HabitDao) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        return HabitListViewModel(habitDao, application as Application) as T
    }
}