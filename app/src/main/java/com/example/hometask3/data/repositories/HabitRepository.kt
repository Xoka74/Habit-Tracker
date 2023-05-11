package com.example.hometask3.data.repositories

import androidx.lifecycle.LiveData
import com.example.hometask3.data.HabitDao
import com.example.hometask3.data.models.entities.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(
    private val habitDao: HabitDao
) {
    fun getAllHabits(): LiveData<List<Habit>> {
        return habitDao.getAllHabits()
    }

    suspend fun insert(habit: Habit) {
        habitDao.insert(habit)
    }

    suspend fun update(habit: Habit) {
        habitDao.update(habit)
    }

    fun getHabitById(id: String): LiveData<Habit> {
        return habitDao.getHabitById(id)
    }
}