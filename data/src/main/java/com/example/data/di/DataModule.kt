package com.example.data.di

import com.example.data.data_sources.HabitApiImpl
import com.example.data.data_sources.HabitApiService
import com.example.data.repositories.HabitDao
import com.example.data.repositories.HabitRepositoryImpl
import com.example.domain.repositories.HabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, LocalDataModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(habitDao)
    }

    @Singleton
    @Provides
    fun provideHabitApi(
        service: HabitApiService,
        @Named("token") token : String
    ): HabitApiImpl {
        return HabitApiImpl(service, token)
    }
}