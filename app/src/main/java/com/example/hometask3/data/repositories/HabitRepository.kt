package com.example.hometask3.data.repositories

import androidx.lifecycle.LiveData
import com.example.hometask3.data.HabitDao
import com.example.hometask3.data.models.entities.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HabitRepository(
    private val habitDao: HabitDao
) {
    fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().flowOn(Dispatchers.IO)
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