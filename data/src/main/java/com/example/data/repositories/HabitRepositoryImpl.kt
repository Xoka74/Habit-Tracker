package com.example.data.repositories

import com.example.domain.models.entities.Habit
import com.example.domain.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository {
    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().flowOn(Dispatchers.IO)
    }

    override suspend fun insert(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitDao.insert(habit)
        }
    }

    override suspend fun update(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitDao.update(habit)
        }
    }

    override fun getHabitById(id: String): Habit {
        return habitDao.getHabitById(id)
    }
}