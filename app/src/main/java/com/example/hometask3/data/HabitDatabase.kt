package com.example.hometask3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hometask3.data.models.entities.Habit

@Database(entities = [Habit::class], version = 6)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        private var instance: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, HabitDatabase::class.java, "habit_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance!!
        }
    }
}