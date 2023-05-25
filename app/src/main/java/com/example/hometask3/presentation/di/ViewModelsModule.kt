package com.example.hometask3.presentation.di

import com.example.data.data_sources.HabitApiImpl
import com.example.domain.repositories.HabitRepository
import com.example.hometask3.presentation.ui.habit_details.HabitViewModelFactory
import com.example.hometask3.presentation.ui.habits_list.HabitListViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelsModule {

    @Singleton
    @Provides
    fun provideHabitListViewModelFactory(
        habitRepository: HabitRepository, habitApi: HabitApiImpl
    ): HabitListViewModelFactory {
        return HabitListViewModelFactory(
            habitRepository,
            habitApi
        )
    }

    @Singleton
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