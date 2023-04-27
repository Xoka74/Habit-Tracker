package com.example.hometask3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hometask3.data.models.Habit
import com.example.hometask3.data.models.HabitType

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getAllHabits(): LiveData<List<Habit>>

    @Insert
    suspend fun insert(habit: Habit)
    @Update
    suspend fun update(habit: Habit)

    @Query("SELECT * FROM Habit WHERE id=:id")
    fun getHabitById(id: Int): LiveData<Habit>
}