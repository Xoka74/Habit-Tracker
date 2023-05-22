package com.example.domain.repositories

import com.example.domain.models.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    fun getHabitById(id: String): Habit
    suspend fun insert(habit: Habit)
    suspend fun update(habit: Habit)
}