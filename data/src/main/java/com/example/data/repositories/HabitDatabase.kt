package com.example.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.models.entities.Habit

@Database(entities = [Habit::class], version = 8)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao
}