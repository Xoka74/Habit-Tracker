package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.repositories.HabitDao
import com.example.data.repositories.HabitDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class LocalDataModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideHabitDatabase(): HabitDatabase {
        return Room.databaseBuilder(context, HabitDatabase::class.java, "habit_database")
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideHabitDao(habitDatabase: HabitDatabase): HabitDao {
        return habitDatabase.getHabitDao()
    }
}