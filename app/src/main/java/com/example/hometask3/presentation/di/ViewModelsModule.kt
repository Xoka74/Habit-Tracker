package com.example.hometask3.presentation.di

import com.example.data.data_sources.HabitApiImpl
import com.example.domain.repositories.HabitRepository
import com.example.hometask3.presentation.ui.habit_details.HabitViewModelFactory
import com.example.hometask3.presentation.ui.habits_list.adapter.HabitListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun provideHabitListViewModelFactory(
        habitRepository: HabitRepository, habitApi: HabitApiImpl
    ): HabitListViewModelFactory {
        return HabitListViewModelFactory(
            habitRepository,
            habitApi
        )
    }

    @Provides
    fun provideHabitViewModelFactory(
        habitRepository: HabitRepository,
        habitApi: HabitApiImpl
    ) : HabitViewModelFactory {
        return HabitViewModelFactory(
            habitRepository,
            habitApi
        )
    }
}