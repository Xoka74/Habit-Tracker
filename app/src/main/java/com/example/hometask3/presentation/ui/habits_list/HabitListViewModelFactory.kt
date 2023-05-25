package com.example.hometask3.presentation.ui.habits_list


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.data.data_sources.HabitApiImpl
import com.example.domain.repositories.HabitRepository
import javax.inject.Inject

class HabitListViewModelFactory @Inject constructor(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApiImpl,
) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        return HabitListViewModel(habitRepository, habitApi, application as Application) as T
    }
}
