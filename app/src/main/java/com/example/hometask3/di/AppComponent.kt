package com.example.hometask3.di

import com.example.data.di.DataModule
import com.example.hometask3.presentation.di.ViewModelsModule
import com.example.hometask3.presentation.ui.filter_bottom_sheet.FilterBottomSheetFragment
import com.example.hometask3.presentation.ui.habit_details.HabitDetailsFragment
import com.example.hometask3.presentation.ui.habits_list.HabitListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ContextModule::class, ViewModelsModule::class])
interface AppComponent {
    fun inject(fragment: HabitDetailsFragment)
    fun inject(fragment: HabitListFragment)
    fun inject(fragment : FilterBottomSheetFragment)
}
