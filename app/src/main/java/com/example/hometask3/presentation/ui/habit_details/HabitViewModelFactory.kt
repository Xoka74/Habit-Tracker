package com.example.hometask3.presentation.ui.habit_details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.data.data_sources.HabitApiImpl
import com.example.domain.repositories.HabitRepository
import javax.inject.Inject

class HabitViewModelFactory @Inject constructor(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApiImpl
) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = extras[APPLICATION_KEY]
        return HabitViewModel(habitRepository, habitApi, app as Application) as T
    }
}
