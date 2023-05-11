package com.example.hometask3.ui.habits_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.hometask3.data.data_sources.HabitApi
import com.example.hometask3.data.repositories.HabitRepository

class HabitListViewModelFactory(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApi,
) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        return HabitListViewModel(habitRepository, habitApi, application as Application) as T
    }
}